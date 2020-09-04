package com.canfer.app.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.model.Documento;
import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.service.DocumentoService;
import com.canfer.app.service.FacturaNotaComplementoService;
import com.canfer.app.storage.FacturaStorageService;
import com.canfer.app.storage.FileStorageService;
import com.canfer.app.webservice.invoiceone.ValidationService;

@Controller
@RequestMapping("/documentosFiscalesClient")
public class DocumentosFiscalesController {
	
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
	FileStorageService fileStorageService;
	
	
	public DocumentosFiscalesController() {
		// Constructor empty
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
			folderName = facturaStorageService.createFacturaDirectory(factura);
			//Get the filename including the parent folders without the root folder.
			filename = facturaStorageService.getFilename(factura, folderName, "xml");
			//Store the XML in the server.
			facturaStorageService.storeFactura(files[0], filename);
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
				facturaStorageService.storeFactura(files[1], filename);
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
	
	@PostMapping(value = "/deleteMultipleFacturas")
	public String deleteMultipleFactura(@RequestParam("idFacturas") Long[] ids) {
		try {
			
			for(Long id : ids) {
				
				// delete the object using the id
				facturaNotaComplementoService.delete(id);
				
				// delete files first, since we use document info to get route
				List<Documento> facturaDocuments = documentoService.findAllByIdTabla(id);
				
				// delete all files 
				facturaDocuments.forEach(document -> {
					// get the route from each document
					Path file = Paths.get(document.getRuta());
					if (file.toFile().exists()) {
						try {
							// delete file if exists
							Files.delete(file);
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				
				// finally delete documents
				documentoService.deleteFacturaDocuments(id);
			}
				
		} catch (Exception e) {
			System.out.println("Ocurrio un error al borrar multiples facturas.");
		}
		return "redirect:/documentosFiscales";	
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteFactura(@PathVariable Long id, Model model) {
		try {
			// delete the object using the id
			facturaNotaComplementoService.delete(id);
			
			// delete files first, since we use document info to get route
			List<Documento> facturaDocuments = documentoService.findAllByIdTabla(id);
			
			// delete all files 
			facturaDocuments.forEach(document -> {
				// get the route from each document
				Path file = Paths.get(document.getRuta());
				if (file.toFile().exists()) {
					try {
						// delete file if exists
						Files.delete(file);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
			// finally delete documents
			documentoService.deleteFacturaDocuments(id);
				
		} catch (Exception e) {
			System.out.println("Ocurrio un error al borrar la factura.");
			model.addAttribute("DeleteFacturaError", e.getMessage());
		}
		return "redirect:/documentosFiscales";	
	}
	
	
	
	
}
