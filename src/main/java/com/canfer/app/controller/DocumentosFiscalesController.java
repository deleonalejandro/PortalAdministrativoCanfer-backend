package com.canfer.app.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileExistsException;
import org.hibernate.boot.jaxb.internal.stax.XmlInfrastructureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Documento;
import com.canfer.app.model.Log;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.service.DocumentoService;
import com.canfer.app.service.ComprobanteFiscalService;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.StorageFileNotFoundException;
import com.canfer.app.webservice.invoiceone.ValidationService;

import javassist.NotFoundException;

@Controller
@RequestMapping("/documentosFiscalesClient")
public class DocumentosFiscalesController {
	
	@Autowired
	private XmlService xmlService;
	@Autowired
	private ComprobanteFiscalService comprobanteService;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private ComprobanteStorageService comprobanteStorageService;
	@Autowired
	private ValidationService validationService;
	@Autowired
	private ComprobanteFiscalRespository comprobanteFiscalRepository; 
	
	public DocumentosFiscalesController() {
		// Constructor empty
	}
	
	@PostMapping("/uploadFactura")
	public String upload(@RequestParam("files") MultipartFile[] files) {
		 
		Comprobante comprobante = null;
		ComprobanteFiscal comprobanteFiscal;
		String ruta;
		
		try {
			//Read the information from the XML.
			comprobante = xmlService.xmlToObject(files[0]);
			
			//Send the information to FacturaNotaComplementoService.
			comprobanteFiscal = comprobanteService.save(comprobante);
			
			/***************************
			 * Save XML into the server 
			 */ 
			
			//Initialize folders and get the route.
			comprobanteStorageService.init(comprobanteFiscal);
			//Store the XML in the server.
			ruta = comprobanteStorageService.store(files[0], comprobanteFiscal);
			//Save document object.
			documentoService.save(comprobanteFiscal, "xml", "Documentos Fiscales", ruta);
			
			/************************************* 
			 *Save PDF into the server
			 */ 
			
			if (!files[1].getOriginalFilename().isEmpty()) {
				//Take the route.
				ruta = comprobanteStorageService.store(files[1], comprobanteFiscal);
				//Save document object.
				documentoService.save(comprobanteFiscal, "pdf", "Documentos Fiscales", ruta);
			}
			
			
			/*********************************************************** 
			 * Use InvoiceOne web service to validate and set responses.
			 *
			 */ 
			comprobanteService.setValidation(comprobanteFiscal, validationService.validaVerifica(files[0]));
				
			
		} catch (FileExistsException e) {
			// handle exception for a duplicated invoice
			Log.activity("Error al intentar guardar factura: " + e.getMessage(), comprobante.getReceptorNombre());
			e.printStackTrace();
		} catch (NotFoundException e) {
			// La empresa o el proveedor no se encuentran en el catalogo
			Log.activity("Error al intentar guardar factura: " + e.getMessage(), comprobante.getReceptorNombre());
			e.printStackTrace();
		} catch (XmlInfrastructureException e) {
			Log.falla("Error al leer el CFD: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// Error inesperado
			Log.activity("Error al intentar guardar factura: Ocurrió un error inesperado", comprobante.getReceptorNombre());
			e.printStackTrace();
		} 
		
		return "redirect:/documentosFiscalesClient";
		
	}
	
	public String saveComprobanteFiscal(@RequestParam ComprobanteFiscalDTO comprobante) {
		try {
			comprobanteService.updateInfo(comprobante);
		} catch (DataAccessResourceFailureException e) {
			Log.falla("Error al actualizar la factura " + comprobante.getUuid() + ": " + e.getMessage());
		}
		
		return "redirect:/documentosFiscalesClient";
	}
	
	@PostMapping(value = "/deleteMultipleFacturas")
	public String deleteMultipleFactura(@RequestParam("idFacturas") Long[] ids) {
		try {
			
			for(Long id : ids) {
				
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
							Log.activity("No se logró eliminar el archivo " + file.getFileName() + ".", document.getEmpresa().getNombre());
							e.printStackTrace();
						}
					}
				});
				
				
				// finally delete documents
				documentoService.deleteFacturaDocuments(id);
				
				// delete the object using the id
				comprobanteService.delete(id);
			}
				
		} catch (Exception e) {
			Log.falla("Ocurrio un error al borrar multiples facturas. " + e.getCause());
		}
		return "redirect:/documentosFiscalesClient";	
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteFactura(@PathVariable Long id, Model model) {
		try {
			
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
						Log.activity("No se logró eliminar el archivo " + file.getFileName() + ".", document.getEmpresa().getNombre());
						e.printStackTrace();
					}
				}
			});
			
			// finally delete documents
			documentoService.deleteFacturaDocuments(id);
			
			// delete the object using the id
			comprobanteService.delete(id);
				
		} catch (Exception e) {
			Log.falla("Ocurrio un error al borrar la factura." + e.getCause());
			model.addAttribute("DeleteFacturaError", e.getMessage());
		}
		return "redirect:/documentosFiscalesClient";	
	}
	
	@GetMapping("/download/{extension:.+}/{id}")
	public ResponseEntity<Resource> downloadLocalFile(@PathVariable Long id, @PathVariable String extension) {
		String contentType = "application/octet-stream";
		Documento doc = documentoService.findByIdTablaAndExtension(id, extension);
		Path path = Paths.get(doc.getRuta());
		Resource resource = null;
		try {
			// try to load resource
			resource = comprobanteStorageService.loadAsResource(path);
		} catch (StorageFileNotFoundException e) {
			Log.activity("Error durante la descarga: " + e.getMessage(), doc.getEmpresa().getNombre());
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body(resource);
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/preview/{extension:.+}/{id}")
	public ResponseEntity<Resource> previewLocalFile(@PathVariable Long id, @PathVariable String extension) {
		String contentType = "application/pdf";
		
		if (extension.equalsIgnoreCase("xml")) {
			contentType = "text/xml";
		}
		
		Documento doc = documentoService.findByIdTablaAndExtension(id, extension);
		Path path = Paths.get(doc.getRuta());
		Resource resource = null;
		try {
			// try to load resource
			resource = comprobanteStorageService.loadAsResource(path);
		} catch (StorageFileNotFoundException e) {
			Log.activity("Error al previsualizar el documento: " + e.getMessage(), doc.getEmpresa().getNombre());
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body(resource);
		} 
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/zip-download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> zipDownload(@RequestParam List<Long> cfdId) {
		// checkout for errors and how to display them
		String zipFileName = "comprobantes_canfer.zip";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);
		List<Documento> docs = new ArrayList<>();
		
		for (Long id : cfdId) {
			
			docs = documentoService.findAllByIdTabla(id);
			
			for (Documento doc : docs) {
				
				Path path = Paths.get(doc.getRuta());
				FileSystemResource resource = new FileSystemResource(path);
				ZipEntry zipEntry = new ZipEntry(resource.getFilename());
				
				try {
					
					zipEntry.setSize(resource.contentLength());
					zipOut.putNextEntry(zipEntry);
					StreamUtils.copy(resource.getInputStream(), zipOut);
					zipOut.closeEntry();
					
				} catch (IOException e) {
					Log.activity("Error al comprimir archivo: "
							+ path.getFileName() + ".", doc.getEmpresa().getNombre());
					e.printStackTrace();
				}
			}
		}
		
		try {
			zipOut.finish();
			zipOut.close();
		} catch (IOException e) {
			Log.activity("Error durante la descarga: No fué posible comprimir los documentos.", docs.get(0).getEmpresa().getNombre());
			e.printStackTrace();
			return ResponseEntity.badRequest()
					.body(bos.toByteArray());
		}
		
	    return ResponseEntity.ok()
	    		.contentType(MediaType.APPLICATION_OCTET_STREAM)
	    		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"")
	    		.body(bos.toByteArray());

	}

	@PostMapping(value = "/getVigencia/{uuid}")
	public void getVigencia(@PathVariable String uuid) {
	
		ComprobanteFiscal comprobante = comprobanteFiscalRepository.findByUuid(uuid);
		String respuestaSat = comprobante.verificaSat(); 
		
		comprobante.setEstatusSAT(respuestaSat);
		comprobanteFiscalRepository.save(comprobante);
		
	}
}
