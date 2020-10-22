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

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.hibernate.boot.jaxb.internal.stax.XmlInfrastructureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Documento;
import com.canfer.app.model.Log;
import com.canfer.app.pdfExport.CrystalReportService;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.PagoRepository;
import com.canfer.app.service.DocumentoService;
import com.canfer.app.service.ComprobanteFiscalService;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.StorageFileNotFoundException;
import com.canfer.app.webservice.invoiceone.ValidationService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import javassist.NotFoundException;
//TODO CONSIDERAR QUE SI NO EXISTE PDF O XML HANDLEAR EL ERROR! PDF GENERICO!!!!!!!!
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
	private ComprobanteFiscalService comprobanteFiscalService; 
	@Autowired
	private ComprobanteFiscalRespository comprobanteFiscalRepository;
	@Autowired
	private CrystalReportService crystalReportService; 
	
	public DocumentosFiscalesController() {
		// Constructor empty
	}
	
	@PostMapping("/uploadFactura")
	public String upload(@RequestParam("files") MultipartFile[] files, @RequestParam String rfc) {
		 
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
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
		
	}

	
	@PostMapping(value = "/deleteMultipleFacturas")
	public String deleteMultipleFactura(@RequestParam List<Long> ids, @RequestParam String rfc) {
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
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;	
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteFactura(@PathVariable Long id, Model model, @RequestParam String rfc) {
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
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;	
	}
	
	//TODO configurar updateError using alert
	@PostMapping(value = "/update")
	public String update(ComprobanteFiscalDTO documento, RedirectAttributes ra, @RequestParam String rfc) {
	
		try {
			comprobanteFiscalService.updateInfo(documento);
		} catch (Exception e) {
			Log.falla("Error al actualizar CFDI: " + e.getMessage());
			ra.addFlashAttribute("updateError", e.getMessage());
		}
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
	}
	
	@GetMapping("/download/{extension:.+}/{id}/{uuid}")
	public ResponseEntity<Resource> downloadLocalFile(@PathVariable String uuid, @PathVariable Long id, @PathVariable String extension) {
		String contentType = "application/octet-stream";
		Documento doc = documentoService.findByConceptoAndExtension(uuid, extension);
		Path path = null; 
		Resource resource = null;
		
		if (doc == null) {
			
			ComprobanteFiscal cfdi = comprobanteFiscalRepository.findById(id).get();
			path = Paths.get(crystalReportService.exportGenerico(id,  cfdi.getUuid()));
			doc = documentoService.findByIdTablaAndExtension(id, extension);
			
		} else {
		path = Paths.get(doc.getRuta());
		}
		
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
	
	@GetMapping("/download/{extension:.+}")
	public ResponseEntity<byte[]> downloadLocalFiles(@RequestParam List<Long> ids, @PathVariable String extension) {
		
		String zipFileName = "comprobantes_canfer.zip";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);
		List<Documento> docs = new ArrayList<>();
		
		for (Long id : ids) {
			
			docs = documentoService.findAllByIdTablaAndExtension(id, extension);
			
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
	
	@GetMapping("/preview/{extension:.+}/{id}")
	public ResponseEntity<Resource> previewLocalFile(@PathVariable Long id, @PathVariable String extension) {
		String contentType = "application/pdf";
		
		if (extension.equalsIgnoreCase("xml")) {
			contentType = "text/xml";
		}
		Path path = null; 
		Documento doc = documentoService.findByIdTablaAndExtension(id, extension);
		if (doc == null) {
			ComprobanteFiscal cfdi = comprobanteFiscalRepository.findById(id).get();
			 path = Paths.get(crystalReportService.exportGenerico(id, cfdi.getUuid()));
			 doc = documentoService.findByIdTablaAndExtension(id, extension);
			
		} else {
		 path = Paths.get(doc.getRuta());
		}
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
	
	@GetMapping("/preview/comprobante/{id}")
	public ResponseEntity<Resource> previewLocalFile(@PathVariable Long id) {
		String contentType = "application/xml";
		
		Factura comprobante = (Factura) comprobanteFiscalRepository.findById(id).get();
		Documento doc = documentoService.findByIdTablaAndExtension(comprobante.getComplemento().getIdComprobanteFiscal(), "xml");
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

	
	@GetMapping(value = "/csv")
	public void exportCSV(HttpServletResponse response, @RequestParam List<Long> ids) throws Exception {

        //set file name and content type
        String filename = "CFDIs.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<ComprobanteFiscal> writer = new StatefulBeanToCsvBuilder<ComprobanteFiscal>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        List<ComprobanteFiscal> list = new ArrayList<>();

		for(Long id : ids) {list.add(comprobanteFiscalRepository.findById(id).get());}
        //write all users to csv file
        writer.write(list);
                
    }
	
	
}
