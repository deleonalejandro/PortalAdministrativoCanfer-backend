package com.canfer.app.model;



import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.dto.DetFormularioCajaChicaDTO;
import com.canfer.app.dto.FormularioCajaChicaDTO;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;
import com.canfer.app.service.ExcelService;

import javassist.NotFoundException;
import jxl.write.WriteException;

@Service("CajaChicaActions")
public class CajaChicaActions extends ModuleActions{
	
	@Autowired
	@Qualifier("DocumentosNacionalesActions")
	private DocumentosNacionalesActions docNacActions;
	
	@Autowired
	private Downloader downloader;
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	private final String ABIERTO = "ABIERTO";
	private final String CANCELADO = "CANCELADO";
	private final String ENVIADO = "ENVIADO";
	private final String PROCESO = "EN REVISION";
	private final String PAGADO = "PAGADO";

	
	

	@Override
	public boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF, Long idSucursal) throws NotFoundException {
		
		String ruta;
		
		if (fileXML != null) {
			
			return docNacActions.upload(fileXML, filePDF);
			
		} else if (filePDF != null) {
			
			Documento doc = new Documento(filePDF);
			
			ruta = comprobanteStorageService.init(createRoute(idSucursal));
			
			doc.accept("new_doc_cajachica", ruta);
			
			superRepo.save(doc);
			
			return true;
			
		} else {
			
			return false;
		}

	}
	
	@Override
	protected boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF) throws FileExistsException, NotFoundException {
		return false;
	}

	
	@Override
	public boolean delete(Long id) {
		
		Optional<DetFormularioCajaChica> df = superRepo.findDetFormularioCCById(id);
		
		if (df.isPresent()) {
			
			FormularioCajaChica dfForm = df.get().getFormularioCajaChica();
			
			if (dfForm.isOpen()) {
				
				superRepo.delete(df.get());
				
				return true;
			}

		}
		
		return false;
	}
	
	public boolean deleteForm(Long id) {
		
		Optional<FormularioCajaChica> fcc = superRepo.findFormularioCCById(id);
		
		if (fcc.isPresent() && fcc.get().isOpen()) {
			
			superRepo.delete(fcc.get());
			
			return true;
		}
		
		return false;
	}
	
	public boolean cancelarForm(Long id) {
		
		Optional<FormularioCajaChica> fcc = superRepo.findFormularioCCById(id);
		
		if (fcc.isPresent() && fcc.get().isOpen()) {
			
			Consecutivo sucursalConsecutivo = superRepo.findConsecutivoBySucursal(fcc.get().getSucursal());
			
			sucursalConsecutivo.getPrevious();
			
			superRepo.save(sucursalConsecutivo);

			superRepo.delete(fcc.get());
			
			return true;
		}
		
		return false;
	}

	@Override
	protected boolean deleteAll(List<Long> ids) {
		return false;
	}

	@Override
	protected ResponseEntity<Resource> download(String method, String repo, Long id, String action) {
		return null;
	}

	@Override
	protected ResponseEntity<byte[]> download(String method, String repo, List<Long> ids) {
		return null;
	}
	
	public ResponseEntity<byte[]> download(Long idFormulario) {
		
		List<Archivo> files = new ArrayList<>();
		
		Optional<FormularioCajaChica> formularioCajaChica = superRepo.findFormularioCCById(idFormulario);
		List<DetFormularioCajaChica> detalles = superRepo.findAllDetFormularioCajaChicaByFormCC(formularioCajaChica.get());
		
		for (DetFormularioCajaChica detalle : detalles) {
			
			Documento doc = detalle.getDocumento();
			
			if (doc.hasXML()) {
				
				files.add(doc.getArchivoXML());
				
			}
			
			if (doc.hasPDF()) {
				
				files.add(doc.getArchivoPDF());
			}
		}
		
		return dowloadManager.downloadZip(files);
		
	}
	
	public ResponseEntity<Resource> download(Long idDocumento, String extension) {
		
		Optional<Documento> documento = superRepo.findDocumentoById(idDocumento);
		
		if (documento.isPresent()) {
			
			if (extension.equalsIgnoreCase("xml") && documento.get().hasXML()) {
				
				return dowloadManager.download(documento.get().getArchivoXML(), "d");
				
			} else if (extension.equalsIgnoreCase("pdf") && documento.get().hasPDF()) {
				
				return dowloadManager.download(documento.get().getArchivoPDF(), "d");
			}
			
		}
		
		return null;
	}
	
	public boolean saveDet(DetFormularioCajaChicaDTO detFormCCDto, ArchivoXML xmlFile, ArchivoPDF pdfFile, Boolean upload) throws NotFoundException {
		
		Optional<FormularioCajaChica> formularioCajaChica;
		Optional<ClasificacionCajaChica> clasificacionCajaChica;
		Optional<Documento> documento;
		
		formularioCajaChica = superRepo.findFormularioCCById(detFormCCDto.getIdFormulario());
		clasificacionCajaChica = superRepo.findClasificacionCCById(detFormCCDto.getIdClasificacion());
		DetFormularioCajaChica detFormCC = new DetFormularioCajaChica();
		
		if (formularioCajaChica.isEmpty() || clasificacionCajaChica.isEmpty()) {
			
			Log.error("Error en la creacion de detalle para el formulario ");
					
			throw new NotFoundException("No se encontraron los atributos necesarios para crear detalle caja chica.");
		
			
		}
		
		if (xmlFile != null) {			
			
			if (!upload) {
				
				Comprobante cfd  = xmlFile.toCfdi();
				
				// check if its a valid cfd.
				if (cfd == null) {
					
					return false;
					
				}
				
				ComprobanteFiscal comprobante = superRepo.findComprobanteByUUID(cfd.getUuidTfd());
				documento = Optional.of(comprobante.getDocumento());
				
			} else {
				
				documento = superRepo.findDocumentoByArchivoXML(xmlFile);

			}
			
			
			if (documento.isPresent()) {
				
				if (!acceptRepeatedDet(documento.get())) {
					return false;
				}
				
				ComprobanteFiscal comprobante = superRepo.findComprobanteByDocumento(documento.get());
					
				detFormCC.setFormularioCajaChica(formularioCajaChica.get());
				detFormCC.setClasificacion(clasificacionCajaChica.get());
				detFormCC.setDocumento(documento.get());
				detFormCC.setFecha(comprobante.getFechaCarga());
				detFormCC.setFolio(comprobante.getFolio());
				detFormCC.setMonto(Float.valueOf(comprobante.getTotal()));
				detFormCC.setBeneficiario(detFormCCDto.getBeneficiario());
				detFormCC.setNombreProveedor(comprobante.getProveedorNombre());
				detFormCC.setVigenciaSat(comprobante.getEstatusSAT());
				
				// make changes to comprobante: idSap, ClaveProveedor (proveedor)
				comprobante.setIdNumSap(formularioCajaChica.get().getFolio());
				comprobante.setProveedor(formularioCajaChica.get().getSucursal().getProveedor());
				comprobante.setCajaChica(true);
				
				// save new info
				superRepo.save(detFormCC);
				superRepo.save(comprobante);
				
				return true;
				
			} else {
				
				return false;

			}
			
			
		} else if (pdfFile != null) {
			
			documento = superRepo.findDocumentoByArchivoPDF(pdfFile);
			
			if (documento.isPresent()) {
				
				pdfFile.rename(String.valueOf(formularioCajaChica.get().getFolio()) + '_' + detFormCCDto.getFolio());
				
				detFormCC.setFormularioCajaChica(formularioCajaChica.get());
				detFormCC.setClasificacion(clasificacionCajaChica.get());
				detFormCC.setDocumento(documento.get());
				detFormCC.setFecha(detFormCCDto.getFormattedDate());
				detFormCC.setFolio(detFormCCDto.getFolio());
				detFormCC.setMonto(detFormCCDto.getMonto());
				detFormCC.setBeneficiario(detFormCCDto.getBeneficiario());
				detFormCC.setNombreProveedor(detFormCCDto.getNombreProveedor());
				
				
				superRepo.save(documento.get());
				superRepo.save(detFormCC);
				
				return true;
				
				
			} else {
				
				return false;
				
			}
			
			
		} else {
			
			throw new NullPointerException("Ambos archivos del detalle se ecuentran vacios.");
		}
		
		
	}
	
	public boolean updateDet(DetFormularioCajaChicaDTO dfDTO) {
		
		/* This method updates the next attributes: clasificacion, nombre de proveedor, fecha, responsable, monto. */
		
		Optional<DetFormularioCajaChica> df = superRepo.findDetFormularioCCById(dfDTO.getIdDetFormularioCC());
		
		if (df.isPresent() && !df.get().hasXML()) {
			
			FormularioCajaChica dfForm = df.get().getFormularioCajaChica();
			
			if (dfForm.isOpen()) {
				
				Optional<ClasificacionCajaChica> clasificacion = superRepo.findClasificacionCCById(dfDTO.getIdClasificacion());
				
				if (clasificacion.isPresent()) {
					df.get().setClasificacion(clasificacion.get());
				}
				
				df.get().setNombreProveedor(dfDTO.getNombreProveedor());
				
				df.get().setFecha(dfDTO.getFormattedDate());
				
				df.get().setBeneficiario(dfDTO.getBeneficiario());
				
				df.get().setMonto(dfDTO.getMonto());
				
				superRepo.save(df.get());
				
				return true;
			}

		}
		
		return false;
		
	}
	
	public FormularioCajaChica newForm(Long idSucursal) {
		
		Optional<Sucursal> sucursal;
		Consecutivo sucursalConsecutivo;
		FormularioCajaChica formCC;
		
		sucursal = superRepo.findSucursalById(idSucursal);
		
		/* take current logged user*/
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		
		if (sucursal.isPresent()) {
			 
			sucursalConsecutivo = superRepo.findConsecutivoBySucursal(sucursal.get());
			
			formCC = new FormularioCajaChica(sucursal.get(), sucursalConsecutivo.getNext(), loggedPrincipal.getName());
			
			/* potential issue with consecutivo not found*/
			superRepo.save(sucursalConsecutivo);
			
			return superRepo.save(formCC); 		

		}
		
		return null;
		
		
	}

	/* This method updates the form's status and comments */
	public boolean updateForm(FormularioCajaChicaDTO formCCDto) {
		
		Optional<FormularioCajaChica> formCC;
		
		formCC = superRepo.findFormularioCCById(formCCDto.getIdFormularioCajaChica());
		
		if (formCC.isPresent()) {
			
			formCC.get().setComentario(formCCDto.getComentario());
			formCC.get().setEstatus(formCCDto.getEstatus());
			formCC.get().setNumeroGuia(formCCDto.getNumeroGuia());
			formCC.get().setPaqueteria(formCCDto.getPaqueteria());
			formCC.get().setNumeroPago(formCCDto.getNumeroPago());
			formCC.get().setFechaPago(formCCDto.getFechaPago());
			
			superRepo.save(formCC.get());
			
			return true;
		}
				
		return false;
		
		
	}
	
	public List<FormularioCajaChica> getAllFormularioCajaChicas(Long idSucursal) {
		
		Optional<Sucursal> sucursal = superRepo.findSucursalById(idSucursal);
		
		if (sucursal.isPresent()) {
			
			return superRepo.findAllFormularioCajaChicaBySucursal(sucursal.get());
		}
		
		return Collections.emptyList();
		
	}
	
	public List<DetFormularioCajaChica> listDetFormularioCajaChica(Long id) {
		
		Optional<FormularioCajaChica> formCC = superRepo.findFormularioCCById(id);
		
		if (formCC.isPresent()) {
			
			return superRepo.findAllDetFormularioCajaChicaByFormCC(formCC.get());
		}
		
		return Collections.emptyList();
		
	}
	
	
	public ResponseEntity<Resource> downloadXls(Long id) {
		
		Optional<FormularioCajaChica> formCC = superRepo.findFormularioCCById(id); 
		
		try {
			
			if (formCC.isPresent()) {
				
				Archivo file = excelService.makeExcel(formCC.get());
				
				return downloader.download(file,"d");
				
			}
			
		} catch (WriteException | IOException e) {
			
			Log.activity("Error al intentar generar un reporte de Excel. ", formCC.get().getSucursal().getProveedor().getNombreEmpresa(), "ERROR_FILE"); 
		}

		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
	}
	
	private Path createRoute(Long idSucursal) {
		
		Optional<Sucursal> sucursal = superRepo.findSucursalById(idSucursal);
		
		if (sucursal.isPresent()) {
			
			LocalDateTime today = LocalDateTime.now();
			
			return Paths.get(sucursal.get().getEmpresa().getRfc(), 
					String.valueOf(today.getYear()),
					String.valueOf(today.getMonthValue()), 
					"Caja-Chica",
					sucursal.get().getClaveProv());
			
		}
		
		return null;
		
		
	}
	
	private Boolean acceptRepeatedDet(Documento documento) {
		
		// todos cancelados si lo paso, al menos uno diferente de cancelado entonces no lo paso
		List<DetFormularioCajaChica> checkDetalles = superRepo.findAllDetFormularioCCByDocumento(documento);
		
		for (DetFormularioCajaChica checkDet : checkDetalles) {
			
			
			FormularioCajaChica checkForm = checkDet.getFormularioCajaChica();
			
			if (!checkForm.isCanceled()) {
				
				Log.activity("Error al intentar guardar detalle: El detalle de caja chica ya se encuentra registrado en este u otro formulario."
						+ " Fo. Formulario: "+ checkDet.getFormularioCajaChica().getFolio()+", Fo. Detalle: "+checkDet.getFolio()+".", 
						checkForm.getSucursal().getEmpresa().getNombre(), "ERROR_DB");		
				
				return false;
				
				}	
			}
		
		return true;
			
		
	}
	
	
	

}
