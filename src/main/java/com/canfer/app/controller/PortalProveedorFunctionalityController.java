package com.canfer.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
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
	public String recieveComprobanteFiscal(@RequestParam("files") MultipartFile[] files, @RequestParam String rfc, 
			@RequestParam String clv, RedirectAttributes ra) {
		
		// initializing directories
		storageService.init();
		
		ArchivoPDF filePDF = null; 
		ArchivoXML fileXML = (ArchivoXML) storageService.storePortalFile(files[0]);
	
		if (!files[1].isEmpty()) {
			
			filePDF = (ArchivoPDF) storageService.storePortalFile(files[1]);
			
		} 
		
		try {
			
			actioner.upload(fileXML, filePDF);
			ra.addFlashAttribute("upload", true);
			
		} catch (NotFoundException e) {
			
			// La empresa o el proveedor no se encuentran en el catalogo
			Log.activity("Error al intentar guardar factura: " + fileXML.toCfdi().getUuidTfd() + ", no se le pudo asignar una empresa o proveedor.", fileXML.toCfdi().getReceptorNombre(), "ERROR_DB");
			ra.addFlashAttribute("upload", true);
		} 
		
		return "redirect:/proveedoresClient?rfc=" + rfc + "&clave=" + clv;
		
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
