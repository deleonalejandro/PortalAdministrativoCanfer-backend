package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;


import com.canfer.app.cfd.Comprobante;
import com.canfer.app.model.Consecutivo;
import com.canfer.app.model.Documento;
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
		if (facturaNotaComplementoRepository.findByUuid(uuid) == null) {
			return false;
		}
		return true;
	}
	
	public void delete(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}
		
		facturaNotaComplementoRepository.delete(fncDocumento);
	}
	
	
	public FacturaNotaComplemento createAndSave(Comprobante comprobante, Documento xmlDocumento, List<String> respuestaValidacion) throws NotFoundException {
		
		FacturaNotaComplemento documentoFiscal = new FacturaNotaComplemento();
		Consecutivo consecutivo;
		//Encuentra a la empresa y el proveedor registrado
		Empresa recepetor = empresaRepository.findByRfc(comprobante.getReceptor().getRfc());
		Proveedor emisor = proveedorRepository.findByRfc(comprobante.getEmisor().getRfc());
		
		if (recepetor == null) {
			throw new NotFoundException("La empresa no existen.");
		}
		
		if (emisor == null) {
			//Creacion de proveedor generico
			emisor = new Proveedor(comprobante.getEmisor().getRfc());
			emisor = proveedorRepository.saveAndFlush(emisor);
		}
		
		/*
		 * XML INFORMATION
		 * 
		 */
		
		//Asignar el consecutivo NumSap dependiendo de la empresa y el modulo
		consecutivo = consecutivoRepository.findByEmpresaAndModulo(recepetor, xmlDocumento.getModulo());
		documentoFiscal.setIdNumSap(consecutivo.getCurrentNum());
		//Aumentar el consecutivo una unidad y guardar en BD.
		consecutivo.setCurrentNum(consecutivo.getCurrentNum() + 1);
		consecutivoRepository.save(consecutivo);
		
		//Set the object fields
		documentoFiscal.setXmlDocumento(xmlDocumento);
		documentoFiscal.setEmpresa(recepetor);
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
		documentoFiscal.setNoCertificadoSAT(comprobante.getComplemento().getTimbreFiscalDigital().getNoCertificadoSat());
		documentoFiscal.setVersionCFD(comprobante.getVersion());
		documentoFiscal.setVersionTimbre(comprobante.getComplemento().getTimbreFiscalDigital().getVersion());
		documentoFiscal.setMoneda(comprobante.getMoneda());
		documentoFiscal.setTotal(comprobante.getTotal());
		documentoFiscal.setTipoDocumento(comprobante.getTipoDeComprobante());
		
		//Documentos UUIDS relacionados
		if (comprobante.getCfdiRelacionados() != null) {
			//Iterate the list and add all UUIDS
			comprobante.getCfdiRelacionados().getCdfiList().forEach(cfdi -> documentoFiscal.addUuidRelacionados(cfdi.getUuid()));	
		}
		
		//Validation PAC responses assignation
		documentoFiscal.setBitValidoSAT(Boolean.valueOf(respuestaValidacion.get(0)));
		documentoFiscal.setRespuestaValidacion(respuestaValidacion.get(1));		
		
		return facturaNotaComplementoRepository.save(documentoFiscal);
	}
	
	public FacturaNotaComplemento save(FacturaNotaComplemento facturaNotaComplemento) {
		return facturaNotaComplementoRepository.save(facturaNotaComplemento);
	}
	
	
	

}
