package com.canfer.app.controller;


import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.service.DocumentoService;
import com.canfer.app.service.FacturaNotaComplementoService;
import com.canfer.app.storage.FacturaStorageService;
import com.canfer.app.webservice.invoiceone.ValidationService;

@Controller
public class FilesController {
	
	@Autowired
	XmlService xmlService;
	@Autowired
	FacturaNotaComplementoService facturaNotaComplementoService;
	@Autowired
	DocumentoService documentoService;
	@Autowired
	FacturaStorageService facturaStorageService;
	@Autowired
	ValidationService validationService;
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	ConsecutivoRepository conrepo;
	
	public FilesController() {
		// Constructor vacio
	}
	
	@PostMapping("/uploadFactura")
	public String upload(@RequestParam("files") MultipartFile[] files) {
		 
		Comprobante comprobante;
		FacturaNotaComplemento factura;
		Path folderName;
		Path filename;
		Path rutaXml;
		Path rutaPdf;
		
		try {
			//Read the information from the XML.
			comprobante = xmlService.xmlToObject(files[0]);
			
			//Send the information to FacturaNotaComplementoService.
			factura = facturaNotaComplementoService.save(comprobante);
			
			/***************************
			 * Save XML into the server
			 * 
			 */ 
			
			//Initialize folders and get the route.
			folderName = facturaStorageService.init(factura);
			//Get the filename including the parent folders without the root folder.
			filename = facturaStorageService.getFilename(factura, folderName, "xml");
			//Store the XML in the server.
			facturaStorageService.store(files[0], filename);
			//Take the route.
			rutaXml = facturaStorageService.load(filename.toString());
			//Save document object.
			documentoService.saveDocumentoFiscal(factura, "xml", "Documentos Fiscales", rutaXml.toString());
			
			/************************************* 
			 * If exists, save PDF into the server
			 *
			 */ 
			
			if (!files[1].getOriginalFilename().isEmpty()) {
				//Get the filename including the parent folders without the root folder.
				filename = facturaStorageService.getFilename(factura, folderName, "pdf");
				//Store the XML in the server.
				facturaStorageService.store(files[1], filename);
				//Take the route.
				rutaPdf = facturaStorageService.load(filename.toString());
				//Save document object.
				documentoService.saveDocumentoFiscal(factura, "pdf", "Documentos Fiscales", rutaPdf.toString());
			}
			
			
			/*********************************************************** 
			 * Use InvoiceOne web service to validate and set responses.
			 *
			 */ 
			facturaNotaComplementoService.setValidation(factura, validationService.validaVerifica(files[0]));
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return "redirect:/documentosFiscales";
		
	}

}
