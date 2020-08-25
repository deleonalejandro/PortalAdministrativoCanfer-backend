package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;


import com.canfer.app.cfd.Comprobante;
import com.canfer.app.model.Consecutivo;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.model.Proveedor;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.FacturaNotaComplementoRepository;
import com.canfer.app.repository.ProveedorRepository;

import javassist.NotFoundException;

@Service
public class FacturaNotaComplementoService {
	
	@Autowired
	FacturaNotaComplementoRepository facturaNotaComplementoRepository;
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	ProveedorRepository proveedorRepository;
	@Autowired
	ConsecutivoRepository consecutivoRepository;
	
	private static final String DOCUMENT_NOT_FOUND = "El documento no existe.";
	
	public List<FacturaNotaComplemento> findAll(){
		
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		
		return facturaNotaComplementoRepository.findAll();		
	}
	
	public FacturaNotaComplemento findById(Long id) {
		Optional<FacturaNotaComplemento> fncDocumento = facturaNotaComplementoRepository.findById(id);
		//Check if the document really exists in the database.
		if (fncDocumento.isEmpty()) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}
		
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		//Logica para rechazar solicitudes no relacionadas al usuario principal.
		
		return fncDocumento.get();
		
	}
	
	public FacturaNotaComplemento findByUUID(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		//Logica para rechazar solicitudes no relacionadas al usuario principal.
		
		return fncDocumento;
	}
	
	public Boolean exist(String uuid) {
		return (facturaNotaComplementoRepository.findByUuid(uuid) != null); 
	}
	
	public void delete(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}
		
		facturaNotaComplementoRepository.delete(fncDocumento);
	}
	
	
	
	public FacturaNotaComplemento save(Comprobante comprobante) throws NotFoundException {
		
		FacturaNotaComplemento documentoFiscal;
		Consecutivo consecutivo;
		Empresa receptor;
		Proveedor emisor;
		
			
		//Get the corresponding objects.
		receptor = empresaRepository.findByRfc(comprobante.getReceptor().getRfc());
		emisor = proveedorRepository.findByRfc(comprobante.getEmisor().getRfc());
		
		if (receptor == null || emisor  == null ) {
			throw new NotFoundException("La empresa o el proveedor no estan registrados en el catalogo. (RFC: " + receptor.getRfc() + emisor.getRfc() + ")");
		}

		documentoFiscal = new FacturaNotaComplemento();
		
		//TODO Considerar SequenceGenerator or CosecutivoService; Considerar hacer tabla de Modulos.
		consecutivo = consecutivoRepository.findByEmpresaAndModulo(receptor, "Documentos Fiscales");
		documentoFiscal.setIdNumSap(consecutivo.getCurrentNum());
		consecutivo.setCurrentNum(consecutivo.getCurrentNum() + 1);
		consecutivoRepository.save(consecutivo);
		
		
		//Set the object fields
		documentoFiscal.setEmpresa(receptor);
		documentoFiscal.setProveedor(emisor);
		
		
		//Use the information from the XML to fill the information
		documentoFiscal.setUuid(comprobante.getComplemento().getTimbreFiscalDigital().getUuid());
		documentoFiscal.setSerie(comprobante.getSerie());
		documentoFiscal.setFolio(comprobante.getFolio());
		documentoFiscal.setRfcEmpresa(comprobante.getReceptor().getRfc());
		documentoFiscal.setRfcProveedor(comprobante.getEmisor().getRfc());
		documentoFiscal.setFechaEmision(comprobante.getFecha());
		documentoFiscal.setFechaTimbre(comprobante.getComplemento().getTimbreFiscalDigital().getFechaTimbrado());
		documentoFiscal.setNoCertificadoEmpresa(comprobante.getNoCertificado());
		documentoFiscal.setNoCertificadoSat(comprobante.getComplemento().getTimbreFiscalDigital().getNoCertificadoSat());
		documentoFiscal.setVersionCfd(comprobante.getVersion());
		documentoFiscal.setVersionTimbre(comprobante.getComplemento().getTimbreFiscalDigital().getVersion());
		documentoFiscal.setMoneda(comprobante.getMoneda());
		documentoFiscal.setTotal(comprobante.getTotal());
		documentoFiscal.setTipoDocumento(comprobante.getTipoDeComprobante());
		
		//Related UUIDs
		if (comprobante.getCfdiRelacionados() != null) {
			//Iterate the list and add all UUIDS
			comprobante.getCfdiRelacionados().getCdfiList().forEach(cfdi -> documentoFiscal.addUuidRelacionados(cfdi.getUuid()));
			documentoFiscal.setTipoRelacionUuidRelacionados(comprobante.getCfdiRelacionados().getTipoRelacion());
		}
			
		return facturaNotaComplementoRepository.save(documentoFiscal);
	}
	
	public FacturaNotaComplemento update(FacturaNotaComplemento facturaNotaComplemento) {
		return facturaNotaComplementoRepository.save(facturaNotaComplemento);
	}
	
	public FacturaNotaComplemento setValidation(FacturaNotaComplemento factura, List<String> respuestas) {
		
		//Update information with the responses from validation service.
		factura.setBitValidoSAT(Boolean.valueOf(respuestas.get(0)));
		factura.setRespuestaValidacion(respuestas.get(1));
		factura.setEstatusSAT(respuestas.get(2));
		
		return facturaNotaComplementoRepository.save(factura);
	}
	
	
	

}
