package com.canfer.app.model;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.ComprobanteFiscal.NotaDeCredito;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.FacturaRepository; 

import javassist.NotFoundException;

@Component
public class DocumentosNacionalesActions extends ModuleActions {

	@Autowired
	private ComprobanteFiscalRespository comprobanteRepo;
	
	@Autowired
	private FacturaRepository facturaRepo;
	
	
	@Override
	public boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF) throws FileExistsException, NotFoundException {
		
		Documento doc;
		
		if (fileXML.businessValidation()) {
			
			if (filePDF == null) {
				
				doc = new Documento(fileXML);
			}
			
			doc = new Documento(fileXML, filePDF);
			
			ComprobanteFiscal cfd = makeCfd(doc);
			
			// fill the object with XML information
			cfd.fill();
			
			// use the PAC to validate the CFD
			cfd.validateInvoiceOne();
			
			// accept the CFD
			cfd.accept();
			
			// persist the entities into DB,
			cfd.save();
			
			// match CFD type P with other CFD.
			if (cfd instanceof ComplementoPago) {
				
				matchFacturaWithComplemento((ComplementoPago) cfd);
				
			}
			
			return true;
			
		} else {
			
			fileXML.discard();
			filePDF.discard();
			
			return false;
		}
		
	}

	@Override
	public boolean delete(Long id) {
		
		Optional<ComprobanteFiscal> value;
		ComprobanteFiscal comprobanteFiscal = null;
		
		value = comprobanteRepo.findById(id);
		
		if (value.isPresent()) {
			
			comprobanteFiscal = value.get();
			
			comprobanteFiscal.delete();
			
			return true;
		}
		
		return false;
		
	}

	@Override
	public boolean deleteAll(List<Long> ids) {
		
		List<ComprobanteFiscal> comprobantes = new ArrayList<>();
				
		try {
			
			comprobantes = comprobanteRepo.findAllById(ids);
			
			comprobantes.forEach(comprobante -> comprobante.delete());
			
			return true;
			
		} catch (Exception e) {
			
			return false;
		}
		
	}
	
	public boolean updateCfdFile(MultipartFile file, Long id) {
		
		ComprobanteFiscal comprobante;
		Optional<ComprobanteFiscal> value = comprobanteRepo.findById(id);
		
		if (value.isPresent()) {
			
			comprobante = value.get();
			
			comprobante.fetchPDF().actualizar(file);
			
			return true;
		
		}else {
			
			return false;
		}
	}
	
	public boolean updateCfdInformation(ComprobanteFiscalDTO documento) {
		
		Optional<ComprobanteFiscal> comprobante = comprobanteRepo.findById(documento.getIdComprobanteFiscal());
		
		if (comprobante.isPresent()) {
			
			try {
				
				return comprobante.get().actualizar(documento);
				
			} catch (Exception e) {
				
				Log.activity("Error al actualizar CFDI: " +documento.getUuid(), comprobante.get().getEmpresaNombre(), "ERROR");
				return false;
			}
			
		} else {
			
			return false;
		}
		
	}
	
	
	
	private ComprobanteFiscal makeCfd(Documento documento) throws NotFoundException {
		
		ComprobanteFiscal comprobanteFiscal;
		String tipoComprobante = documento.getArchivoXML().toCfdi().getTipoDeComprobante();
		
		if (tipoComprobante.equalsIgnoreCase("I")) {
			
			comprobanteFiscal = new Factura(documento);
			

		} else if (tipoComprobante.equalsIgnoreCase("E")) {
			
			comprobanteFiscal = new NotaDeCredito(documento); 
			

		} else if (tipoComprobante.equalsIgnoreCase("P")) {
			
			comprobanteFiscal = new ComplementoPago(documento);
			
		} else {
			// throw error since no document type was found
			throw new NotFoundException("No se encontro el tipo de documento para procesar el comprobante fiscal. ("+tipoComprobante+")");
		}
		
		return comprobanteFiscal;
		
	}
	
	private void matchFacturaWithComplemento (ComplementoPago complementoPago) {
		
		Factura factura;
		
		try {
			
			// the related document is identified by a string variable containing the UUID for a particular invoice.
			for (String docRelacionado : complementoPago.getUuidRelacionadosList()) {
				
				factura = facturaRepo.findByUuid(docRelacionado);
				
				if (factura != null) {
					
					factura.setComplemento(complementoPago);
					
					facturaRepo.save(factura);
					
				}
			}
			
		} catch (NullPointerException e) {
			
			Log.falla(e.getMessage(), "ERROR");
		}
		
		
	}

	@Override
	public void downloadCsv(List<Long> ids, HttpServletResponse response) {

		List<IModuleEntity> comprobantes = comprobanteRepo.findByAllEntityById(ids);
		
		dowloadManager.downloadCSV(comprobantes, response);
		
		
	}
	

	

}
