/* Fecha de Creacion-Modificacion: 08/09/2020
 * 
 * Clase: ComprobanteFiscalService
 * 
 * Descripcion: La clase esta clasificada como un servicio, el cual utiliza todos los repositorios y servicios necesarios
 * par el procesamiento y guardado de un objeto comprobante: Factura, Nota de Credito, Complemento de Pago, en la base de datos.  
 * 
 * */

package com.canfer.app.service;

import java.util.List;
import java.util.Optional;


import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.model.Consecutivo;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.ComprobanteFiscal.NotaDeCredito;
import com.canfer.app.repository.ComplementoPagoRepository;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.FacturaRepository;
import com.canfer.app.repository.NotaDeCreditoRepository;
import com.canfer.app.repository.ProveedorRepository;

import javassist.NotFoundException;

@Service
public class ComprobanteFiscalService {

	@Autowired
	private ComprobanteFiscalRespository comprobanteFiscalRepository;
	@Autowired
	private FacturaRepository facturaRepository;
	@Autowired
	private NotaDeCreditoRepository notaDeCreditoRepository;
	@Autowired
	private ComplementoPagoRepository complementoPagoRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired
	private ConsecutivoRepository consecutivoRepository;

	private static final String DOCUMENT_NOT_FOUND = "El comprobante fiscal no existe en la base de datos.";

	public ComprobanteFiscal save(Comprobante comprobante) throws FileExistsException, NotFoundException {

		Consecutivo consecutivo;
		Long idNumSap;
		Empresa receptor;
		Proveedor emisor;
		List<Proveedor> proveedores;

		if (exist(comprobante.getUuidTfd())) {
			throw new FileExistsException("El comprobante fiscal ya se encuentra registrado en la base de datos. UUID: "
					+ comprobante.getUuidTfd() + " Emisor: " + comprobante.getEmisor());
		}

		receptor = empresaRepository.findByRfc(comprobante.getReceptorRfc());
		proveedores = proveedorRepository.findAllByEmpresasAndRfc(receptor, comprobante.getEmisorRfc());

		// check if the company or the provider exist on the data base.
		if (receptor == null) {
			throw new NotFoundException("La empresa o el proveedor no estan registrados en el catalogo. "
					+ "Nombre Empresa: " + comprobante.getReceptorNombre() + " Empresa RFC: " + comprobante.getReceptorRfc() + "."); 
		}
		
		// get the proper provider
		if (proveedores.size() > 1 || proveedores.isEmpty()) {
			// more than one found in the query for PROVEEDOR, use PROVEEDOR GENERICO instead.
			emisor = proveedorRepository.findByEmpresasAndNombre(receptor, "PROVEEDOR GENÉRICO");
		} else {
			emisor = proveedores.get(0);
		}

		// use the proper sequence for the company and module
		consecutivo = consecutivoRepository.findByEmpresaAndModulo(receptor, "Documentos Fiscales");
		idNumSap = consecutivo.getNext();
		consecutivoRepository.save(consecutivo);

		if (comprobante.getTipoDeComprobante().equalsIgnoreCase("I")) {
			Factura factura = new Factura(comprobante, receptor, emisor, idNumSap);
			facturaRepository.save(factura);
			return factura;

		} else if (comprobante.getTipoDeComprobante().equalsIgnoreCase("E")) {
			NotaDeCredito nota = new NotaDeCredito(comprobante, receptor, emisor, idNumSap);
			notaDeCreditoRepository.save(nota);
			return nota;

		} else if (comprobante.getTipoDeComprobante().equalsIgnoreCase("P")) {
			ComplementoPago complemento = new ComplementoPago(comprobante, receptor, emisor, idNumSap);
			complementoPagoRepository.save(complemento);
			return complemento;
		} else {
			// throw error since no document type was found
			throw new NotFoundException("No se encontro el tipo de documento para procesar el comprobante fiscal. ("+comprobante.getTipoDeComprobante()+")");
		}

	}
	
	public ComprobanteFiscal updateInfo(ComprobanteFiscalDTO comprobanteDTO) {
		
		ComprobanteFiscal comprobanteUpdate = findByUUID(comprobanteDTO.getUuid());
		
		// TODO work on this will be needed... stay tuned with the front end
		
		//comprobanteUpdate.setProveedor(comprobanteDTO.getProveedor());}
		
		comprobanteUpdate.setBitRSusuario(comprobanteDTO.getBitRSusuario());
		System.out.println(comprobanteDTO.getBitRSusuario());
		comprobanteUpdate.setComentario(comprobanteDTO.getComentario());
		
		return comprobanteFiscalRepository.save(comprobanteUpdate);
	}

	public ComprobanteFiscal setValidation(ComprobanteFiscal comprobante, List<String> respuestas) {

		// Update information with the responses from validation service.
		comprobante.setBitValidoSAT(Boolean.valueOf(respuestas.get(0)));
		comprobante.setRespuestaValidacion(respuestas.get(1));
		comprobante.setEstatusSAT(respuestas.get(2));

		return comprobanteFiscalRepository.save(comprobante);
	}

	public List<ComprobanteFiscal> findAll() {
		return comprobanteFiscalRepository.findAll();
	}

	public ComprobanteFiscal findById(Long id) {
		Optional<ComprobanteFiscal> fncDocumento = comprobanteFiscalRepository.findById(id);
		// Check if the document really exists in the database.
		if (fncDocumento.isEmpty()) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}

		// Logica para reconocer el usuario activo; devolver documentos pertinentes al
		// usuario
		// Logica para rechazar solicitudes no relacionadas al usuario principal.

		return fncDocumento.get();

	}

	public void delete(Long id) {
		comprobanteFiscalRepository.deleteById(id);
	}

	public ComprobanteFiscal findByUUID(String uuid) {
		ComprobanteFiscal fncDocumento = comprobanteFiscalRepository.findByUuid(uuid);
		// Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}
		// Logica para reconocer el usuario activo; devolver documentos pertinentes al
		// usuario
		// Logica para rechazar solicitudes no relacionadas al usuario principal.

		return fncDocumento;
	}

	public void delete(String uuid) {
		ComprobanteFiscal fncDocumento = comprobanteFiscalRepository.findByUuid(uuid);
		// Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(DOCUMENT_NOT_FOUND);
		}

		comprobanteFiscalRepository.delete(fncDocumento);
	}
	
	private boolean exist(String uuid) {
		return (comprobanteFiscalRepository.findByUuid(uuid) != null);
	}

}
