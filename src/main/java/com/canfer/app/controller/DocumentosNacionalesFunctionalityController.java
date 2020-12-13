package com.canfer.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.model.DocumentosNacionalesActions;
import com.canfer.app.model.Log;
import com.canfer.app.model.Prueba;
import com.canfer.app.storage.ComprobanteStorageService;

import javassist.NotFoundException;

@Controller
@RequestMapping("/documentosFiscalesClient")
public class DocumentosNacionalesFunctionalityController {

	@Autowired
	private DocumentosNacionalesActions actioner; 
	
	@Autowired
	private ComprobanteStorageService storageService; 
	
	@PostMapping("/uploadFactura")
	public String recieveComprobanteFiscal(@RequestParam("files") MultipartFile[] files, @RequestParam String rfc) {
		
		Prueba prueba = new Prueba();
		prueba.print();
		
		// initializing directories
		storageService.init();
		
		ArchivoPDF filePDF = null; 
		ArchivoXML fileXML = (ArchivoXML) storageService.storePortalFile(files[0]);
	
		if (!files[1].isEmpty()) {
			
			filePDF = (ArchivoPDF) storageService.storePortalFile(files[1]);
			
		} 
		
		try {
			
			actioner.upload(fileXML, filePDF);
			
		} catch (FileExistsException e) {
			
			// handle exception for a duplicated invoice
			Log.activity("Error al intentar guardar factura: "+fileXML.toCfdi().getUuidTfd()+ " el archivo ya existe.", fileXML.toCfdi().getReceptorNombre(), "ERROR_DB");
						
		} catch (NotFoundException e) {
			
			// La empresa o el proveedor no se encuentran en el catalogo
			Log.activity("Error al intentar guardar factura: " + fileXML.toCfdi().getUuidTfd() + ", no se le pudo asignar una empresa o proveedor.", fileXML.toCfdi().getReceptorNombre(), "ERROR_DB");

		} 
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
		
	}
	
	@GetMapping("/download/{method}/{repo}/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, repo, id, "d");
		
	}
	
	
	@GetMapping("/download/{method}/{repo}/")
	public ResponseEntity<byte[]> download(@RequestParam List<Long> ids, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, repo, ids);
	
	}
	
	@GetMapping("/preview/{method}/{repo}/{id}")
	public ResponseEntity<Resource> preview(@PathVariable Long id, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, repo, id, "p");
		
	}
	
	@GetMapping("/csv/{repo}/")
	public void download(HttpServletResponse response, @RequestParam List<Long> ids) {
		
		actioner.downloadCsv(ids, response);
		
	}
	
	@PostMapping(value = "/deleteMultipleFacturas")
	public String deleteMultipleComprobanteFiscal(@RequestParam List<Long> ids, @RequestParam String rfc) {
		
		actioner.deleteAll(ids);
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
			
			
	}
	
	@PostMapping(value = "/delete/{id}")
	public String deleteComprobanteFiscal(@PathVariable Long id, @RequestParam String rfc) {
		
		actioner.delete(id);
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
			
			
	}
	
	@PostMapping(value = "/update")
	public String update(ComprobanteFiscalDTO documento,  @RequestParam String rfc, @RequestParam("pdf") MultipartFile pdf) {
		
		
		if (pdf != null) {
			
			actioner.updateCfdFile(pdf, documento.getIdComprobanteFiscal());
			
		}
		
		// update object information normally 
		actioner.updateCfdInformation(documento);
			
	
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
	}
	


	
}
