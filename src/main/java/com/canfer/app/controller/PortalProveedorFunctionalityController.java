package com.canfer.app.controller;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.model.Log;
import com.canfer.app.model.PortalProveedorActions;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.storage.ComprobanteStorageService;

import javassist.NotFoundException;

@Controller
@RequestMapping("proveedoresClient")
public class PortalProveedorFunctionalityController {
	
	@Autowired
	@Qualifier("PortalProveedorActions")
	private PortalProveedorActions actioner;
	
	@Autowired
	private ComprobanteStorageService storageService; 

	
	public PortalProveedorFunctionalityController() {
		// default controller
	}
	
	@PostMapping("/uploadFactura")
	public ResponseEntity<String> recieveComprobanteFiscal(@RequestParam("files") MultipartFile[] files) throws JSONException {
		JSONObject response = new JSONObject();
		// initializing directories
		storageService.init();
		
		ArchivoPDF filePDF = null; 
		if (!files[0].isEmpty()) {
			
			ArchivoXML fileXML = (ArchivoXML) storageService.storePortalFile(files[0]);
			
				if (!files[1].isEmpty()) {
					
					filePDF = (ArchivoPDF) storageService.storePortalFile(files[1]);
					
				} 
			
			try {
				
				boolean isUploaded = actioner.upload(fileXML, filePDF);
				
				if (isUploaded) {
					response.put("status", isUploaded);
					response.put("desc", "El documento CFDI se ha cargado exitosamente.");
				} else {
					response.put("status", isUploaded);
					response.put("desc", "Error de carga: El documento CFDI ya se encuentra registrado en el portal.");
				}
				
				
			} catch (NotFoundException | JSONException e) {
				
				// La empresa o el proveedor no se encuentran en el catalogo
				Log.activity("Error al intentar guardar factura: " + fileXML.toCfdi().getUuidTfd() + ", no se le pudo asignar una empresa o proveedor.", fileXML.toCfdi().getReceptorNombre(), "ERROR_DB");
				response.put("status", false);
				response.put("desc", "Error de carga: " + fileXML.toCfdi().getUuidTfd() + ", no corresponde a ninguna empresa o proveedor.");
			} 
			
		} else {
			
			response.put("status", false);
			response.put("desc", "Error de carga: La solicitud no contiene al menos un archivo CFDI XML.");
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		
	}
	
	@GetMapping("/download/{method}/{repo}/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id, @PathVariable String method) {
		
		return actioner.download(method, "Pago", id, "d");
		
	}
	
	@GetMapping("/preview/{method}/{repo}/{id}")
	public ResponseEntity<Resource> preview(@PathVariable Long id, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, "Pago", id, "p");
		
	}
	
	@GetMapping("/csv")
	public void download(@RequestParam String rfc, @RequestParam String clave, HttpServletResponse response) {

			actioner.downloadCsv(response, rfc, clave);
	
		
	}
	
	@GetMapping("/excel")
	public ResponseEntity<Resource> downloadExcel(@RequestParam String rfc, @RequestParam String clave) {
		
		return actioner.downloadXls(rfc, clave);
	}
	

}
