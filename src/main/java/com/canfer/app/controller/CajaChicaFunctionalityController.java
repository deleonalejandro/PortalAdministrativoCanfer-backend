package com.canfer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.DetFormularioCajaChicaDTO;
import com.canfer.app.model.CajaChicaActions;
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
	 * @param claveProv
	 * @param rfcEmpresa
	 * @return FormularioCajaChica
	 */
	@GetMapping("/newformcc")
	@ResponseBody
	public FormularioCajaChica newFormCC(@RequestParam("clv") String claveProv, @RequestParam("rfc") String rfcEmpresa) {
		
		return actioner.newForm(claveProv, rfcEmpresa);
		
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
	
	/*********************************************
	 * GUARDAR NUEVO DETALLE FORMULARIO CAJA CHICA
	 * 
	 * @param detFormCCDto - DTO DetFormularioCajaChica.
	 * @param mFileXML - MultipartFile for XML.
	 * @param mFilePDF - MultipartFile for PDF.
	 * @param ra - RedirectAttributes for HTTP request.
	 */
	public void saveDetalleFormCC(DetFormularioCajaChicaDTO detFormCCDto, MultipartFile mFileXML, MultipartFile mFilePDF, RedirectAttributes ra) {
		
		ArchivoXML fileXML = null;
		ArchivoPDF filePDF = null;
		
		// initializing directories
		storageService.init();
		
		if (mFileXML.isEmpty() && mFilePDF.isEmpty()) {
			
			ra.addFlashAttribute("upload", false);
			
		} else {
			
			try {
				
				if (!mFileXML.isEmpty()) {
					
					fileXML = (ArchivoXML) storageService.storePortalFile(mFileXML);
					
				}
				
				if (!mFilePDF.isEmpty()) {
					
					filePDF = (ArchivoPDF) storageService.storePortalFile(mFilePDF);
					
				}
				
				boolean value = actioner.upload(fileXML, filePDF, detFormCCDto.getIdFormulario());
				
				ra.addFlashAttribute("upload", value);
				
				
			} catch (StorageException e) {
				
				Log.falla(e.getMessage(), "ERROR_DB");
				
				ra.addFlashAttribute("upload", false);
				
			} catch (NotFoundException e) {
				
				Log.activity(e.getMessage(), fileXML.getReceptor(), "ERROR_DB");
				
				ra.addFlashAttribute("upload", false);
				
			}
			
			
			
		}
		
		actioner.saveDet(detFormCCDto, fileXML, filePDF);
		
	}
	

}
