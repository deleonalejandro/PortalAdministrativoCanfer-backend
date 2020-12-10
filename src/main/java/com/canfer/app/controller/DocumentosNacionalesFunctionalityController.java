package com.canfer.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.ModuleActions;
import com.canfer.app.storage.ComprobanteStorageService;


public class DocumentosNacionalesFunctionalityController {

	@Autowired
	private ModuleActions actioner; 
	
	@Autowired
	private ComprobanteStorageService storageService; 
	
	@PostMapping("/uploadFactura")
	public String recieveComprobanteFiscal(@RequestParam("files") MultipartFile[] files, @RequestParam String rfc) {
		
		ArchivoPDF filePDF = null; 
		ArchivoXML fileXML = storageService.storeXML(files[0]);
	
		if (files.length > 1) {
			
			filePDF = storageService.storePDF(files[1]);
			
		} 
		
		actioner.upload(fileXML, filePDF);
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
		
	}
	
	

	@GetMapping("/download/{method}/{repo}/")
	public ResponseEntity<byte[]> download(@RequestParam List<Long> ids, @PathVariable String method, @PathVariable String repo) {
		
		actioner.download(ids);
		return null;
		
	}
	
	@GetMapping("/csv/{repo}/")
	public ResponseEntity<byte[]> download(HttpServletResponse response, @RequestParam List<Long> ids, @PathVariable String repo) {
		
		actioner.downloadCSV(ids, response);
		return null;
		
	}
	
	@PostMapping(value = "/deleteMultipleFacturas")
	public String deleteMultipleFactura(@RequestParam List<Long> ids, @RequestParam String rfc) {
		return rfc;
		
			
			
	}
	
	@PostMapping(value = "/delete/{id}")
	public String deleteFactura(@PathVariable Long id, @RequestParam String rfc) {
		return rfc;
		
			
			
	}
	
	@GetMapping("/preview/{repo}/{id}")
	public ResponseEntity<byte[]> preview(@PathVariable Long id) {
		
		actioner.preview(id);
		return null;
		
	}

	
}
