package com.canfer.app.model;




import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.canfer.app.dto.DetFormularioCajaChicaDTO;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
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
	
	

	@Override
	public boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF, Long idSucursal) throws NotFoundException {
		
		String ruta;
		
		if (fileXML != null) {
			
			return docNacActions.upload(fileXML, filePDF);
			
		} else if (filePDF != null) {
			
			/* TODO Save the PDF files as XML files, use the method found in Actioner DF*/
			
			//Generar ruta: Facturas/PortalProveedores/RFCEmpresa/year/month/cajachica/
			
			//create document
			Documento doc = new Documento(filePDF);
			
			//create route
			ruta = comprobanteStorageService.init(createRoute(idSucursal));
			
			//create name
			
			//accept the document
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

	@Override
	protected boolean deleteAll(List<Long> ids) {
		return false;
	}

	@Override
	protected ResponseEntity<Resource> download(String method, String repo, Long id, String action) {
		
		Optional<Archivo> archivo = superRepo.findArchivoById(id);
		
		if (archivo.isPresent()) {
			
			return dowloadManager.download(archivo.get(), action);
		}
		
		return null;
	}

	@Override
	protected ResponseEntity<byte[]> download(String method, String repo, List<Long> ids) {
		return null;
	}
	
	
	public boolean saveDet(DetFormularioCajaChicaDTO detFormCCDto, ArchivoXML xmlFile, ArchivoPDF pdfFile) {
		
		Optional<FormularioCajaChica> formularioCajaChica;
		Optional<ClasificacionCajaChica> clasificacionCajaChica;
		Optional<Documento> documento;
		
		formularioCajaChica = superRepo.findFormularioCCById(detFormCCDto.getIdFormulario());
		clasificacionCajaChica = superRepo.findClasificacionCCById(detFormCCDto.getIdClasificacion());
		DetFormularioCajaChica detFormCC = new DetFormularioCajaChica();
		
		if (xmlFile != null) {
			
			documento = superRepo.findDocumentoByArchivoPDF(pdfFile); 
			
		} else if (pdfFile != null) {
			
			documento = superRepo.findDocumentoByArchivoXML(xmlFile);
			
			pdfFile.rename(formularioCajaChica.get().getFolio() + '_' + detFormCCDto.getFolio());
		
		} else {
			
			throw new NullPointerException("Ambos archivos del detalle se ecuentran vacios.");
		}
		
		
		if (formularioCajaChica.isPresent() && clasificacionCajaChica.isPresent()) {
				
			detFormCC.setFormularioCajaChica(formularioCajaChica.get());
			detFormCC.setClasificacion(clasificacionCajaChica.get());
			detFormCC.setDocumento(documento.get());
			detFormCC.setFecha(detFormCCDto.getFormattedFechaDet());
			detFormCC.setFolio(detFormCCDto.getFolio());
			detFormCC.setMonto(detFormCCDto.getMonto());
			detFormCC.setResponsable(detFormCCDto.getResponsable());
			
			
			superRepo.save(documento.get());

			superRepo.save(detFormCC);
			
			
			return true;
		}
		
		return false;
		
		
	}
	
	public boolean updateDet() {
		
		/* This method updates the next attributes: clasificacion, nombre de proveedor, fecha, responsable, monto. */
		
		return false;
		
	}
	
	public FormularioCajaChica newForm(Long idSucursal) {
		
		Optional<Sucursal> sucursal;
		Consecutivo sucursalConsecutivo;
		FormularioCajaChica formCC;
		
		sucursal = superRepo.findSucursalById(idSucursal);
		
		if (sucursal.isPresent()) {
			 
			sucursalConsecutivo = superRepo.findConsecutivoBySucursal(sucursal.get());
			
			/* potential issue with consecutivo not found*/
			superRepo.save(sucursalConsecutivo);
			
			formCC = new FormularioCajaChica(sucursal.get(), sucursalConsecutivo.getNext());
			
			return superRepo.save(formCC); 		

		}
		
		return null;
		
		
	}

	/* This method updates the form's status and comments */
	public boolean updateForm(Long idForm, String estatus, String comentario) {
		
		Optional<FormularioCajaChica> formCC;
		
		formCC = superRepo.findFormularioCCById(idForm);
		
		if (formCC.isPresent()) {
			
			formCC.get().setComentario(comentario);
			formCC.get().setEstatus(estatus);
			
			superRepo.save(formCC.get());
			
			return true;
		}
				
		return false;
		
		
	}
	
	public List<DetFormularioCajaChica> showDetFormularioCajaChica(Long id) {
		
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
		
		Optional<Proveedor> sucursal = superRepo.findProveedorById(idSucursal);
		
		if (sucursal.isPresent()) {
			
			LocalDateTime today = LocalDateTime.now();
			
			return Paths.get(sucursal.get().getEmpresasRfc().get(0), 
					String.valueOf(today.getYear()),
					String.valueOf(today.getMonthValue()), 
					"Caja-Chica",
					sucursal.get().getClaveProv());
			
		}
		
		return null;
		
		
	}
	

}
