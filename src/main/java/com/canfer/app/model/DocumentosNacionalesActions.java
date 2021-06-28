package com.canfer.app.model;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.dto.ProveedorDTO;
import com.canfer.app.dto.UserDTO;
import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.ComprobanteFiscal.NotaDeCredito;
import com.canfer.app.pdfExport.CrystalReportService;
import com.canfer.app.service.ExcelService;
import com.canfer.app.service.ProveedorService;
import com.canfer.app.service.UsuarioService;

import javassist.NotFoundException;
import jxl.write.WriteException;

@Service("DocumentosNacionalesActions")
public class DocumentosNacionalesActions extends ModuleActions {
	
	@Autowired
	private ExcelService xlsService; 
	
	@Autowired
	private Downloader downloader;

	@Autowired
	private EmailSenderService emailSender; 
	
	@Autowired
	private CrystalReportService crystalService; 
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CajaChicaActions ccActioner;
	
	@Override
	public boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF) throws NotFoundException {
		
		Documento doc;
		String ruta;
		
		if (businessValidation(fileXML)) {
			
			if (filePDF == null) {
				
				doc = new Documento(fileXML);
				
			} else {
				
				doc = new Documento(fileXML, filePDF);
				
			}
			
			
			ComprobanteFiscal cfd = makeCfd(doc);
			
			// fill the object with XML information
			cfd.fill();
			
			// use the PAC to validate the CFD
			cfd.validateInvoiceOne();
			
			cfd.verificaSat();
			
			// prepare the new route for the accepted file
			ruta = comprobanteStorageService.init(Paths.get(cfd.createRoute()));
			
			// accept the CFD
			cfd.accept(ruta);
			
			// persist the entities into DB,
			superRepo.save(cfd);
			
			// match CFD type P with other CFD.
			if (cfd instanceof ComplementoPago) {
				
				matchFacturaWithComplemento((ComplementoPago) cfd);
				
			}
		
			emailSender.sendEmailNewDoc(cfd);
			
			Log.activity("Se ha guardado el comprobante fiscal No." + cfd.getIdNumSap() + ". Emisor: "+ cfd.getProveedorNombre()+", UUID: "+ cfd.getUuid()+"." , cfd.getEmpresaNombre(), "NEW_DOC");
			return true;
			
		} else {
			
			fileXML.discard(storageProperties.getErrorLocation());
			
			if (filePDF != null) {

				filePDF.discard(storageProperties.getErrorLocation());

			} 
			
			return false;
		}
		
		
	}
	
	public ResponseEntity<Resource> download(String method, String repo, Long id, String action) {
		
		Optional<Pago> valuePago;
		Optional<ComprobanteFiscal> valueComprobante;
		ComprobanteFiscal comprobante;
		
		IModuleEntity entity = null;
		
		// switching the repository strategies for single files.
		switch (repo) {
		
		case "Pago":
			
			valuePago = superRepo.findPagoById(id);
			
			if (valuePago.isPresent()) {
				
				entity = valuePago.get();
			}
				
			break;
			
		case "ComprobanteFiscal":
			
			valueComprobante =  superRepo.findComprobanteById(id);
			
			if (valueComprobante.isPresent()) {
				
				entity = valueComprobante.get();
			}
			
			break;

		default:
			break;
		}
		
		// switching the method strategies for single files.
		switch (method) {
		
		case "singleXML":
			
			return dowloadManager.download(entity.fetchXML(), action);

		case "singlePDF":
			
			if (entity.fetchPDF() != null) {
					
				return dowloadManager.download(entity.fetchPDF(), action);
			
			} else if (entity instanceof ComprobanteFiscal){
				
				
				ArchivoPDF archivoGen = crystalService.exportGenerico((ComprobanteFiscal) entity);

				return dowloadManager.download(archivoGen, action);
				
				
			} 
			
		case "singlePayment":
			
			comprobante = (ComprobanteFiscal) entity;
			
			return dowloadManager.download(comprobante.getPago().fetchPDF(), action);
			
		default:
			break;
			
		}
		
		return null;
	}
	
	public ResponseEntity<byte[]> download(String method, String repo, List<Long> ids) {

		List<Archivo> files = new ArrayList<>();
		List<ComprobanteFiscal> comprobantes = new ArrayList<>();

		// switching the repository strategies for single files.
		switch (repo) {

		case "ComprobanteFiscal":

			comprobantes = superRepo.findAllComprobanteById(ids);

			break;

		default:
			break;
		}

		switch (method) {

		case "zipXML":

			for (ComprobanteFiscal comprobanteFiscal : comprobantes) {

				files.add(comprobanteFiscal.fetchXML());

			}

			break;

		case "zipPDF":

			for (ComprobanteFiscal comprobanteFiscal : comprobantes) {

				if (comprobanteFiscal.getDocumento().hasPDF()) {

					files.add(comprobanteFiscal.fetchPDF());

				}

			}

			break;

		case "zip":

			for (ComprobanteFiscal comprobanteFiscal : comprobantes) {

				files.add(comprobanteFiscal.fetchXML());

				if (comprobanteFiscal.getDocumento().hasPDF()) {

					files.add(comprobanteFiscal.fetchPDF());

				}

			}

			break;

		default:
			break;
		}

		return dowloadManager.downloadZip(files);

	}
	
	public ResponseEntity<Resource> downloadXls(List<Long> ids) {
		
		List<ComprobanteFiscal> comprobantes = superRepo.findAllComprobanteById(ids); 
		
		try {
			
			Archivo file = xlsService.makeExcel(comprobantes);
			return downloader.download(file,"d");
			
		} catch (WriteException | IOException e) {
			
			Log.activity("Error al intentar generar un reporte de Excel. ", comprobantes.get(0).getEmpresaNombre(), "ERROR_FILE"); 
			return null;
		}

		
	}

	@Override
	public boolean delete(Long id) {
		
		Optional<ComprobanteFiscal> value;
		ComprobanteFiscal comprobanteFiscal = null;
		Documento doc;
		Optional<DetFormularioCajaChica> detCC;
		
		value = superRepo.findComprobanteById(id);
		
		if (value.isPresent()) {
			
			comprobanteFiscal = value.get();

			// clear the cfd from any related files.
			if(comprobanteFiscal instanceof ComplementoPago) {
				clearComplemento((ComplementoPago) comprobanteFiscal);
			}
			
			if (comprobanteFiscal instanceof Factura && ((Factura) comprobanteFiscal).getHasComplemento()) {
				return false;	
			}
			
			// clear cfd from CAJA CHICA dets.
			if(!clearDetsCajaChica(comprobanteFiscal)) {
				return false;
			}
			
			try {
				
				comprobanteFiscal.delete();
				
			} catch (NullPointerException e) {
				Log.activity("El documento perteneciente a la CFD con Fo. " + comprobanteFiscal.getFolio() + " no se eliminó exitosamente.", comprobanteFiscal.getEmpresaNombre(), "ERROR_DB");
			}
			
			Log.activity("Se ha eliminado el documento fiscal No. " + comprobanteFiscal.getIdNumSap() + "." , comprobanteFiscal.getEmpresaNombre(), "DELETE");
			
			superRepo.delete(comprobanteFiscal);
			
			
			return true;
		}
		
		return false;
		
	}

	@Override
	public boolean deleteAll(List<Long> ids) {

		List<ComprobanteFiscal> comprobantes = new ArrayList<>();
				
		try {
			
			comprobantes = superRepo.findAllComprobanteById(ids);
			
			comprobantes.forEach(comprobante -> {
				
				if(comprobante instanceof ComplementoPago) {
					clearComplemento((ComplementoPago) comprobante);
				}
				
				if (comprobante instanceof Factura
						&& ((Factura) comprobante).getComplemento() != null) {
						
					
				} else {
				
					comprobante.delete();
					superRepo.delete(comprobante);
					
					Log.activity("Se ha eliminado el documento fiscal No. " + comprobante.getIdNumSap() + "." , comprobante.getEmpresaNombre(), "DELETE");
				
				}
				
				});
			
			
			return true;
			
		} catch (Exception e) {
			
			return false;
		}
		
	}
	
	public boolean updateCfdFile(MultipartFile file, Long id) {
		
		ComprobanteFiscal comprobante;
		Optional<ComprobanteFiscal> value = superRepo.findComprobanteById(id);
		
		if (value.isPresent()) {
			
			comprobante = value.get();
			
			if (comprobante.fetchPDF() == null) {
				
				// uploading newpdf to the system.
				ArchivoPDF newPdf = (ArchivoPDF) comprobanteStorageService.storePortalFile(file);
				// creating route from the XML file.
				Path rutaFromXML = Paths.get(comprobante.fetchXML().getRuta());
				// getting name from the uploaded file.
				String[] nameParts = newPdf.getNombre().split("\\.");
				// accepting the document and moving from previous file location to final storage.
				newPdf.accept(nameParts[0], rutaFromXML.getParent().toString());
				// binding the new pdf to the CFDI document. 
				comprobante.fetchDocument().setArchivoPDF(newPdf);
				
			} else {
				
				comprobante.fetchPDF().actualizar(file);
			}
			
			return true;
		
		}else {
			
			return false;
		}
	}
	
	public String updateCfdInformation(ComprobanteFiscalDTO documento) {
		
		Optional<Proveedor> proveedor = Optional.empty();
		ComprobanteFiscal comprobanteUpdate;
		Optional<ComprobanteFiscal> comprobante = superRepo.findComprobanteById(documento.getIdComprobanteFiscal());
		
		if (comprobante.isPresent()) {
			
			//Checar que la clave del proveedor del comprobante sea consistente 	
			if(documento.getIdProveedor() != null) {

				proveedor = superRepo.findProveedorById(documento.getIdProveedor());
			}
			
			try {
				
				int choice = comprobante.get().actualizar(documento, proveedor); 
				
				if(choice == 1) {
					
					comprobanteUpdate = superRepo.save(comprobante.get());
					emailSender.sendEmailUpdateDoc(comprobanteUpdate);
					return "true";
					
				} else if (choice == 2 || choice == 3) {
					
					comprobanteUpdate = superRepo.save(comprobante.get());
					emailSender.sendEmailNewDoc(comprobanteUpdate);
					return "true";
					
				}else {
				
					superRepo.save(comprobante.get());
					return "none";
				}
				
				
			} catch (Exception e) {
				
				Log.activity("Error al actualizar CFDI: " + documento.getUuid(), comprobante.get().getEmpresaNombre(), "ERROR_UPDATE");
				return "false";
			}
			
			
		} else {
			
			return "false";
		}
		
	}
	

	
	public String refreshEstatusSat(Long id) {
		
		String respuestaSat;
		
		Optional<ComprobanteFiscal> comprobante = superRepo.findComprobanteById(id);
		
		if(comprobante.isPresent()) {
			
			respuestaSat = comprobante.get().verificaSat();
				
			superRepo.save(comprobante.get());
				
			return respuestaSat;

			
		} else {
			
			return null;
		}
		
	}
	
	public boolean updateSupplier(ProveedorDTO proveedor) {
		
		proveedorService.updateProveedor(proveedor);
		
		return true;
	}
	
	public boolean deleteSupplier(Long id) throws NotFoundException {
		
		proveedorService.delete(id);
		
		return true;
	}
	
	public boolean saveNewUserSupplier(UserDTO user) throws NotFoundException, EntityExistsException, SQLDataException {
		
		usuarioService.saveUsuarioProveedor(user);
		
		return true;
	}
	
	private ComprobanteFiscal makeCfd(Documento documento) throws NotFoundException {
		
		ComprobanteFiscal comprobanteFiscal;
		Comprobante model;
		String tipoComprobante;
		Empresa receptor;
		Proveedor emisor;
		Consecutivo consecutivo;
		List<Proveedor> proveedores;
		
		model = documento.getArchivoXML().toCfdi();
		tipoComprobante = model.getTipoDeComprobante();
		
		receptor = superRepo.findEmpresaByRFC(model.getReceptorRfc());
		proveedores = superRepo.findAllProveedorByEmpresaAndRFC(receptor, model.getEmisorRfc());
		
		
		/* if no supplier was found */
		if (proveedores.isEmpty()) {
			
			emisor = superRepo.findProveedorByEmpresasAndNombre(receptor, "PROVEEDOR GENÉRICO");
			
		} else {
			
			/* check if supplier has the same currency, reject if not */
			for (Proveedor proveedor:proveedores) {
				
				if (proveedor.getMoneda() != null) {
					
					if (!proveedor.getMoneda().contains(model.getMoneda())) {
						proveedores.remove(proveedor);
					}
					
				}
			}
			
			if (proveedores.isEmpty() || proveedores.size() > 1) {
				
				emisor = superRepo.findProveedorByEmpresasAndNombre(receptor, "PROVEEDOR GENÉRICO");
				
			} else {
				
				emisor = proveedores.get(0);
			}
			
		}
		
		

		// use the proper sequence for the company and module
		consecutivo = superRepo.findConsecutivoByEmpresaAndModulo(receptor, "Documentos Fiscales");
		
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
		
		// assigning base values to the CFD
		comprobanteFiscal.setEmpresa(receptor);
		comprobanteFiscal.setProveedor(emisor);
		comprobanteFiscal.setIdNumSap(consecutivo.getNext());
		
		superRepo.save(consecutivo);
		
		return comprobanteFiscal;
		
	}
	
	private void matchFacturaWithComplemento (ComplementoPago complementoPago) {
		
		Factura factura;
		
		try {
			
			// the related document is identified by a string variable containing the UUID for a particular invoice.
			for (String docRelacionado : complementoPago.getUuidRelacionadosList()) {
				
				factura = superRepo.findFacturaByUUID(docRelacionado);
				
				if (factura != null) {
					
					factura.setComplemento(complementoPago);
					
					superRepo.save(factura);
					
				}
			}
			
		} catch (NullPointerException e) {
			
			Log.falla(e.getMessage(), "ERROR");
		}
		
		
	}
	
	private boolean clearComplemento(ComplementoPago complementoPago) {
	    
	    List<Factura> facturas = superRepo.findAllFacturaByComplemento(complementoPago);

	    if (!facturas.isEmpty()) {
	    	
	      facturas.forEach(factura -> factura.removeComplemento());
	      
	      superRepo.saveAllFactura(facturas);
	    }
	    
	    return true;

	  }
	
	private boolean clearDetsCajaChica(ComprobanteFiscal comprobanteFiscal) {
		
		Documento doc;
		List<DetFormularioCajaChica> detsCC;
		
		// check if the cfd lives in other modules
		doc = comprobanteFiscal.getDocumento();
		
		// delete the detCC from CAJA CHICA MODULE.
		detsCC = superRepo.findAllDetFormularioCCByDocumento(doc);
		
		for (DetFormularioCajaChica det : detsCC) {
			
			if(!ccActioner.forceDelete(det.getIdDetFormularioCajaChica())) {
				
				Log.activity("Error al borrar el detalle de caja chica con Fo. "+ det.getFolio() +" y formulario con Fo. " + det.getFormularioCajaChica().getFolio() 
						+ " que incluye el comprobante fiscal: " + comprobanteFiscal.getIdNumSap(), comprobanteFiscal.getEmpresaNombre(), "DELETE");
				
				return false;
			}
	
		}
		
		return true;
		
					
		
	}
	

	private Boolean businessValidation(ArchivoXML archivo) {
		
		// if the xml file is not read it will return a null value.
		Comprobante comprobante = archivo.toCfdi();
		
		if (comprobante == null) {
			
			return false;
			
		}
		
		// the code continues if the value is not null, proceeding to get the company.
		Empresa receptor = superRepo.findEmpresaByRFC(comprobante.getReceptorRfc());
		
		// check if uuid is in DB.
		if (exist(comprobante.getUuidTfd())) {
			
			Log.activity("Error al intentar guardar factura: El comprobante fiscal ya se encuentra registrado en la base de datos. UUID: "
					+ comprobante.getUuidTfd() + " Emisor: " + comprobante.getEmisorRfc(), comprobante.getReceptorNombre(), "ERROR_DB");
			
			return false;
			
		}
		
		// check if the company or the provider exist on the data base.
		if (receptor == null) {
			
			Log.activity("Error al intentar guardar factura: La empresa no se encuentra registrada en el catálogo. "
					+ "Empresa: " + comprobante.getReceptorNombre() + "RFC: " + comprobante.getReceptorRfc() + ".", comprobante.getReceptorNombre(), "ERROR_DB"); 
			
			return false;
		}
		
		// adding company stamp
		archivo.setReceptor(comprobante.getReceptorNombre());
		
		return true; 
		
	}

	
	private boolean exist(String uuid) {
		return (superRepo.findComprobanteByUUID(uuid) != null);
	}

	// Methods not used by this class

		@Override
		protected boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF, Long idSucursal)
				throws FileExistsException, NotFoundException {
			return false;
		}
		



		

	}
