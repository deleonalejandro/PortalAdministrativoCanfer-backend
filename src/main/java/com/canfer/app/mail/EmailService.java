package com.canfer.app.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.persistence.NoResultException;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.service.DocumentoService;
import com.canfer.app.service.FacturaNotaComplementoService;
import com.canfer.app.storage.FacturaStorageService;
import com.canfer.app.storage.FileStorageService;
import com.canfer.app.storage.StorageException;
import com.canfer.app.webservice.invoiceone.ValidationService;

import javassist.NotFoundException;

@Service
public class EmailService {
	
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private XmlService xmlService;
	@Autowired
	private FacturaNotaComplementoService facturaNotaComplementoService;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private FacturaStorageService facturaStorageService;
	@Autowired
	private ValidationService validationService;
	
	public EmailService() {
		// Constructor vacio
	}

	public void handleEmails(Message[] messages) {

		List<BodyPart> attachments;
		
		attachments = getAllAttachments(messages);
		
		if (attachments.isEmpty()) {
			throw new NoResultException("No existen archivos adjuntos.");
		}

		// initializing directories
		fileStorageService.init();
		
		// iterate bodyPart list
		for (BodyPart attachment : attachments) {

			try {

				// get extension of body part attachment
				String fileName = attachment.getFileName();
				String extension = FilenameUtils.getExtension(fileName);

				// make sure there is an attachment with extension
				if (extension != null) {
					if (extension.equalsIgnoreCase("zip")) {
						// process the ZIP file
						zipExtraction(attachment);
					}

					else {
						// Save the documents into the base folder
						fileStorageService.storeAttachment(attachment);
					}

				}
				
			} catch (StorageException | MessagingException e) {
				System.out.println("No fue posible recibir el archivo." + e.getMessage());
			}

		}

		if (processDocuments()) {
			System.out.println("Los documentos fueron procesados satisfactoriamente. La bandeja de entrada ha sido vaciada.");
		}

	}
	
	public boolean processDocuments() {
		
		// list of XML paths
		List<Path> filePaths = fileStorageService.loadAllRoutes()
				.filter(file -> FilenameUtils.getExtension(file.getFileName().toString()).equalsIgnoreCase("xml"))
				.collect(Collectors.toList());

		for (Path path : filePaths) {
			
			// Extract the base name from the xml file
			String baseName = FilenameUtils.getBaseName(path.getFileName().toString());
			
			// Filter the directory and throw the paths that contain the base name on it
			// Sort the list (.pdf comes first)
			List<Path> files = fileStorageService.loadAllRoutes()
					.filter(file -> file.getFileName().toString().contains(baseName)).sorted(Comparator.reverseOrder())
					.collect(Collectors.toList());

			
			// Move files to corresponding official directories
			saveOfficialDocuments(files);
			
			// Erase files from the entries directory
			files.forEach(file -> {
				if (file.toFile().exists()) {
					try {
						// delete file if exists
						Files.delete(file);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}

		// clear entries directory
		Stream<Path> remainingFiles = fileStorageService.loadAllRoutes();
		
		if (remainingFiles.findAny().isPresent()) {
			// finally copy remaining files into the unknown directory
			fileStorageService.loadAllRoutes().forEach(file -> fileStorageService.migrateAttachments(file, "OTHER"));
			fileStorageService.deleteAll();
		}
		
		return true;
	}
	
	public void saveOfficialDocuments(List<Path> files) {
		
		Comprobante comprobante;
		FacturaNotaComplemento factura;
		Path folderName;
		Path filename;
		Path rutaXml;
		Path rutaPdf;
		
		
		try {
			
			//Read the information from the XML.
			comprobante = xmlService.xmlToObject(files.get(0));
			
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
			facturaStorageService.storeFactura(files.get(0), filename);
			
			//Take the route.
			rutaXml = facturaStorageService.load(filename.toString());
			
			//Save document object.
			documentoService.saveDocumentoFiscal(factura, "xml", "Documentos Fiscales", rutaXml.toString());
			
			/************************************* 
			 * If exists, save PDF into the server
			 *
			 */ 
			
			if (files.size() > 1) {
				//Get the filename including the parent folders without the root folder.
				filename = facturaStorageService.getFilename(factura, folderName, "pdf");
				
				//Store the XML in the server.
				facturaStorageService.storeFactura(files.get(1), filename);
				
				//Take the route.
				rutaPdf = facturaStorageService.load(filename.toString());
				
				//Save document object.
				documentoService.saveDocumentoFiscal(factura, "pdf", "Documentos Fiscales", rutaPdf.toString());
				
			}
			
			
			/*********************************************************** 
			 * Use InvoiceOne web service to validate and set responses.
			 *
			 */ 
			facturaNotaComplementoService.setValidation(factura, validationService.validaVerifica(files.get(0)));
			
			
			/******************************************************
			 * Moving files to the storage directory, double-check
			 * 
			 */
			
			files.forEach(file -> fileStorageService.migrateAttachments(file, "OK"));
			
		} catch (FileExistsException e) {
			//La factura ya existe
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			e.printStackTrace();
		} catch (NotFoundException e) {
			//La empresa o el proveedor no se encuentran en el catalogo
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			e.printStackTrace();
		} catch (Exception e) {
			// Error inesperado
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			e.printStackTrace();
		}
	}
	
	public void zipExtraction(BodyPart bodyPart) {
		try {
			
			InputStream is = bodyPart.getInputStream();

			// iterate the entries
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry ze = zis.getNextEntry();
			
			while (ze != null) {
				
				String zeFileName = ze.getName();
				
				if (!zeFileName.contains("MACOSX")) {

					ByteArrayOutputStream out = new ByteArrayOutputStream();
					IOUtils.copy(zis, out);
					InputStream targetStream = new ByteArrayInputStream(out.toByteArray());

					//save the zip attachments
					fileStorageService.storeZipAttachment(targetStream, ze);

				}

				zis.closeEntry();
				ze = zis.getNextEntry();
			}

			// close last ZipEntry
			zis.closeEntry();
			zis.close();

		} catch (IOException | MessagingException e) {
			// La lectura del archivo ZIP fue interrumpida
			e.printStackTrace();
		}

	}
	
	public List<BodyPart> getAllAttachments(Message[] messages) {

		List<BodyPart> bodyParts = new ArrayList<>();

		for (int i = 0; i < messages.length; i++) {
			Message msg = messages[i];
			// get content of message
			
			try {
				
				Multipart multipart = (Multipart) msg.getContent();
				
				for (int j = 0; j < multipart.getCount(); j++) {
					
					BodyPart bodyPart = multipart.getBodyPart(j);
					String fileName = bodyPart.getFileName();
					String extension = FilenameUtils.getExtension(fileName);
					
					// check if extension is not null
					if (fileName == null && extension == null) {
						continue;
					}
					
					if (((extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("xml"))
							&& (bodyPart.getDisposition() != null)) || extension.equalsIgnoreCase("zip")) {
						
						// add body part to the list
						bodyParts.add(bodyPart);
					}
					
				}
				
			} catch (MessagingException e) {
				// Ocurrio un error durante la lectura del mensaje.
			} catch (IOException e) {
				// No fue posible extraer el contenido del mensaje
			}
		}
		
		return bodyParts;
	}
	 
	
}
