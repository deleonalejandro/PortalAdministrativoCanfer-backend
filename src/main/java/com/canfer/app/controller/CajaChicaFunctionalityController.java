package com.canfer.app.controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.canfer.app.dto.DetFormularioCajaChicaDTO;
import com.canfer.app.dto.FormularioCajaChicaDTO;
import com.canfer.app.model.CajaChicaActions;
import com.canfer.app.model.ClasificacionCajaChica;
import com.canfer.app.model.DetFormularioCajaChica;
import com.canfer.app.model.FormularioCajaChica;
import com.canfer.app.model.GenericResponse;
import com.canfer.app.model.Log;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.StorageException;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;

import javassist.NotFoundException;

@Controller
@RequestMapping("/cajachicaclient")
public class CajaChicaFunctionalityController implements HandlerExceptionResolver {
	
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
	
	@GetMapping("/openformcc")
	public ResponseEntity<Boolean> openFormCC(@RequestParam Long id) {
		
		if(actioner.openForm(id)) {

			return new ResponseEntity<>(true, HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/deletecanceledformcc")
	public ResponseEntity<Boolean> deleteCanceledFormCC(@RequestParam Long id) {
		
		if(actioner.deleteNotOpenForm(id)) {
			
			return new ResponseEntity<>(true, HttpStatus.OK);

		} else {
			
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		
	}

	@GetMapping("/cancelarformcc")
	public ResponseEntity<Object> cancelarFormCC(@RequestParam Long id) {
		
		actioner.cancelarForm(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/updateformcc")
	public ResponseEntity<GenericResponse> updateFormCC(FormularioCajaChicaDTO formCCDto) {
		
		if(actioner.updateForm(formCCDto)) {
			return new ResponseEntity<>(new GenericResponse("La información del formulario con se actualizó exitosamente.", true), HttpStatus.OK);
		}
		return new ResponseEntity<>(new GenericResponse("Ocurrió un problema al actualizar la información del formulario.", false), HttpStatus.OK);
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
	public ResponseEntity<GenericResponse> saveDetalleFormCC(DetFormularioCajaChicaDTO detFormCCDto, @RequestParam("xml") MultipartFile mFileXML, @RequestParam("pdf") MultipartFile mFilePDF, Model model,
			@CookieValue("suc") Long idSucursal) {
		
		JSONObject response = new JSONObject();
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
				
				return new ResponseEntity<>(new GenericResponse(e.getMessage(), upload), HttpStatus.OK);
				
			} catch (NotFoundException e) {
				
				Log.activity(e.getMessage(), fileXML.getReceptor(), "ERROR_DB");
				
				upload = false;
				
				return new ResponseEntity<>(new GenericResponse(e.getMessage(), upload), HttpStatus.OK);
				
			}	
			
		}
		
		try {
			
			// get the boolean value for the existence or not of the CFDI
			fileLoaded = upload;
			// get boolean for success or not of the Det genertion.
			upload = actioner.saveDet(detFormCCDto, fileXML, filePDF, upload);
			
		} catch (NotFoundException e) {
			Log.activity(e.getMessage(), fileXML.getReceptor(), "ERROR_DB");
			return new ResponseEntity<>(new GenericResponse(e.getMessage(), false), HttpStatus.OK);
		} catch (StorageException|EntityExistsException|NullPointerException e) {
			return new ResponseEntity<>(new GenericResponse(e.getMessage(), false), HttpStatus.OK);
		}
		// return response depending if its a new document or not and successful or not.
		if (fileLoaded) {			
			if(upload) {
				return new ResponseEntity<>(new GenericResponse("El detalle de formulario se creo exitosamente.", upload), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new GenericResponse("Ocurrio un error inesperado al crear el detalle.", upload), HttpStatus.OK);
			}
		} else {
			if(upload) {
				return new ResponseEntity<>(new GenericResponse("El detalle se creo exitosamente.", "df", upload), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new GenericResponse("Ocurrio un error inesperado al crear el detalle.", "df", upload), HttpStatus.OK);
			}
		}
		
	}
	
	@GetMapping("/deletedetformcc")
	public ResponseEntity<Object> deleteDetalleFormCC(@RequestParam Long id) {
		
		if (actioner.delete(id)) {
			
			return new ResponseEntity<>(HttpStatus.OK);
			
		}
		
		return new ResponseEntity<>("ERROR", HttpStatus.OK);
	}
	
	@PostMapping("/updatedetformcc")
	public ResponseEntity<GenericResponse> updateDetalleFormCC(DetFormularioCajaChicaDTO data, @RequestParam("pdf") MultipartFile pdf) {
		
		return new ResponseEntity<>(actioner.updateDet(data, pdf), HttpStatus.OK);
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
	
	@GetMapping("/loadallcanceledforms")
	@ResponseBody
	public List<FormularioCajaChica> getAllCanceledFormularioCC(@RequestParam Long idSucursal) {
		
		return actioner.getAllCanceledFormularioCajaChicas(idSucursal);
	}
	
	@GetMapping("/addclasificacion")
	public String addClasificacion(@RequestParam String clasificacion) {
		
		actioner.addClasificacionCajaChica(clasificacion);
		
		return "redirect:/admin/clasificaciones/cc";
	}
	
	@GetMapping("/removeclasificacion")
	public String addClasificacion(@RequestParam("id") Long clasificacion) {
		
		actioner.removeClasificacionCajaChica(clasificacion);
		
		return "redirect:/admin/clasificaciones/cc";
	}
	
	@GetMapping("/getclasificaciones")
	@ResponseBody
	public List<ClasificacionCajaChica> getClasificaciones() {
		return actioner.getAllClasificaciones();
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

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		String referrer = request.getHeader("referer");
		ModelAndView modelAndView = new ModelAndView("redirect:" + referrer);
	    if (ex instanceof MaxUploadSizeExceededException) {
	        modelAndView.getModel().put("message", "File size exceeds limit!");
	    }
	    return modelAndView;
	}
	

}
