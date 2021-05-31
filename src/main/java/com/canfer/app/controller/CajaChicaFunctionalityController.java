package com.canfer.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.dto.DetFormularioCajaChicaDTO;
import com.canfer.app.dto.FormularioCajaChicaDTO;
import com.canfer.app.model.CajaChicaActions;
import com.canfer.app.model.DetFormularioCajaChica;
import com.canfer.app.model.FormularioCajaChica;
import com.canfer.app.model.Log;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.StorageException;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;

import javassist.NotFoundException;

@Controller
@RequestMapping("/cajachicaclient")
public class CajaChicaFunctionalityController {
	
	@Autowired
	@Qualifier("CajaChicaActions")
	private CajaChicaActions actioner;
	
	@Autowired
	private ComprobanteStorageService storageService;
	
	/***********************************
	 * CREAR NUEVO FORMULARIO CAJA CHICA
	 * 
	 * @param id - Sucursal id
	 * @return FormularioCajaChica
	 */
	@GetMapping("/newformcc")
	@ResponseBody
	public FormularioCajaChica newFormCC(@CookieValue("suc") Long idSucursal) {
		
		return actioner.newForm(idSucursal);
		
	}
	
	/*******************************
	 * BORRAR FORMULARIO CAJA CHICA
	 * 
	 * @param id - FormularioCajaChica object id.
	 */
	@GetMapping("/deleteformcc")
	public ResponseEntity<Object> deleteFormCC(@RequestParam Long id) {
		
		actioner.deleteForm(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/*******************************
	 * BORRAR FORMULARIO CAJA CHICA
	 * 
	 * @param id - FormularioCajaChica object id.
	 */
	@GetMapping("/cancelarformcc")
	public ResponseEntity<Object> cancelarFormCC(@RequestParam Long id) {
		
		actioner.cancelarForm(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/updateformcc")
	public ResponseEntity<String> updateFormCC(FormularioCajaChicaDTO formCCDto) {
		
		Boolean updateBoolean = actioner.updateForm(formCCDto);
		
		return new ResponseEntity<>(updateBoolean.toString(), HttpStatus.OK);
	}
	
	/*********************************************
	 * GUARDAR NUEVO DETALLE FORMULARIO CAJA CHICA
	 * 
	 * @param detFormCCDto - DTO DetFormularioCajaChica.
	 * @param mFileXML - MultipartFile for XML.
	 * @param mFilePDF - MultipartFile for PDF.
	 * @param ra - RedirectAttributes for HTTP request.
	 */
	@PostMapping("/savedetformcc")
	public ResponseEntity<String> saveDetalleFormCC(DetFormularioCajaChicaDTO detFormCCDto, @RequestParam("xml") MultipartFile mFileXML, @RequestParam("pdf") MultipartFile mFilePDF, Model model,
			@CookieValue("suc") Long idSucursal) {
		
		
		ArchivoXML fileXML = null;
		ArchivoPDF filePDF = null;
		Boolean upload;
		Boolean fileLoaded = false;
		detFormCCDto.setIdSucursal(idSucursal);
		
		// initializing directories
		storageService.init();
		
		if (mFileXML.isEmpty() && mFilePDF.isEmpty()) {
			
			upload = false;
			
		} else {
			
			try {
				
				if (!mFileXML.isEmpty()) {
					
					fileXML = (ArchivoXML) storageService.storePortalFile(mFileXML);
					
				}
				
				if (!mFilePDF.isEmpty()) {
					
					filePDF = (ArchivoPDF) storageService.storePortalFile(mFilePDF);
					
				}
				
				upload = actioner.upload(fileXML, filePDF, detFormCCDto.getIdSucursal());
	
			} catch (StorageException e) {
				
				Log.falla(e.getMessage(), "ERROR_DB");
				
				upload = false;
				
				return new ResponseEntity<>(upload.toString(), HttpStatus.OK);
				
			} catch (NotFoundException e) {
				
				Log.activity(e.getMessage(), fileXML.getReceptor(), "ERROR_DB");
				
				upload = false;
				
				return new ResponseEntity<>(upload.toString(), HttpStatus.OK);
				
			}	
			
		}
		
		try {
			
			// get the boolean value for the existence or not of the CFDI
			fileLoaded = upload;
			// get boolean for success or not of the Det genertion.
			upload = actioner.saveDet(detFormCCDto, fileXML, filePDF, upload);
			
		} catch (NotFoundException e) {
			
			Log.activity(e.getMessage(), fileXML.getReceptor(), "ERROR_DB");
			
		}
		
		// return response depending if its a new document or not and successful or not.
		if (fileLoaded) {			
			return new ResponseEntity<>("new-"+upload, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("pull-"+upload, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/deletedetformcc")
	public ResponseEntity<Object> deleteDetalleFormCC(@RequestParam Long id) {
		
		if (actioner.delete(id)) {
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		
		return new ResponseEntity<>("ERROR", HttpStatus.OK);
	}
	
	@GetMapping("/updatedetformcc")
	public ResponseEntity<Object> updateDetalleFormCC(DetFormularioCajaChicaDTO data) {
		
		if (actioner.updateDet(data)) {
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		
		return new ResponseEntity<>("ERROR", HttpStatus.OK);
	}
	
	
	
	@GetMapping("/loadformdetails")
	@ResponseBody
	public List<DetFormularioCajaChica> getDetallesFormularioCC(@RequestParam("id") Long idFormCC) {
		
		return actioner.listDetFormularioCajaChica(idFormCC);
		
	}
	
	@GetMapping("/loadallforms")
	@ResponseBody
	public List<FormularioCajaChica> getAllFormularioCC(@RequestParam Long idSucursal) {
		
		return actioner.getAllFormularioCajaChicas(idSucursal);
	}
	
	@GetMapping("/download/{extension}")
	public ResponseEntity<Resource> download(@RequestParam Long id, @PathVariable String extension) {
		
		return actioner.download(id, extension);	
	}
	
	@GetMapping("/zip")
	public ResponseEntity<byte[]> downloadZip(@RequestParam("id") Long idFormulario) {
		
		return actioner.download(idFormulario);
		
	}
	
	@GetMapping("/excel")
	public ResponseEntity<Resource> downloadExcel(@RequestParam Long id) {
		
		return actioner.downloadXls(id);
		
	}
	

}
