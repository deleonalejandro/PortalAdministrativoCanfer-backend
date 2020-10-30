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
import org.hibernate.boot.jaxb.internal.stax.XmlInfrastructureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Log;
import com.canfer.app.service.DocumentoService;
import com.canfer.app.service.ComprobanteFiscalService;
import com.canfer.app.storage.ComprobanteStorageService;
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
	private ComprobanteFiscalService comprobanteFiscalService;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private ComprobanteStorageService comprobanteStorageService;
	@Autowired
	private ValidationService validationService;
	@Autowired
	private EmailSenderService emailSender; 

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
				Log.falla("Error al procesar correo: No fue posible recibir el archivo adjunto. " + e.getMessage());
			}

		}

		if (processDocuments()) {
			Log.general("Los correos fueron procesados satisfactoriamente. La bandeja de entrada ha sido vaciada.");
		}

	}

	private boolean processDocuments() {

		List<Path> filePaths = new ArrayList<>();
		
		// list of XML paths
		try {
			
			filePaths = fileStorageService.loadAllRoutes()
					.filter(file -> FilenameUtils.getExtension(file.getFileName().toString()).equalsIgnoreCase("xml"))
					.collect(Collectors.toList());
			
		} catch (StorageException e2) {
			Log.falla("Error al procesar documentos descargados: " + e2.getMessage());
		}

		for (Path path : filePaths) {

			List<Path> files = new ArrayList<>();
			String baseName = FilenameUtils.getBaseName(path.getFileName().toString());

			// Filter the directory and throw the paths that contain the base name on it.
			try {
				
				files = fileStorageService.loadAllRoutes()
						.filter(file -> file.getFileName().toString().contains(baseName)).sorted(Comparator.reverseOrder())
						.collect(Collectors.toList());

			} catch (Exception e2) {
				Log.falla("Error al procesar documentos descargados: " + e2.getMessage());
			}

			// Move files to corresponding official directories
			saveOfficialDocuments(files);

			// Erase files from the entries directory
			files.forEach(file -> {
				if (file.toFile().exists()) {
					try {
						// delete file if exists
						Files.delete(file);

					} catch (IOException e) {
						Log.falla("Error al procesar documentos descargados: No fue posible eliminar el archivo del directorio de descargas." + e.getMessage());
						e.printStackTrace();
					}
				}
			});
		}
		
		try {
			
			// clear entries directory
			Stream<Path> remainingFiles = fileStorageService.loadAllRoutes();
			
			if (remainingFiles.findAny().isPresent()) {
				// finally copy remaining files into the unknown directory and delete all from the entries.
				fileStorageService.loadAllRoutes().forEach(file -> fileStorageService.migrateAttachments(file, "OTHER"));
				fileStorageService.deleteAll();
			}
			
		} catch (StorageException e2) {
			Log.falla("Error al procesar documentos descargados: " + e2.getMessage());
		}


		return true;
	}

	private void saveOfficialDocuments(List<Path> files) {

		Comprobante comprobante = null;
		ComprobanteFiscal comprobanteFiscal;
		String ruta; 
		List<String> validationResponse;

		try {
			// Read the information from the XML.
			comprobante = xmlService.xmlToObject(files.get(0));

			// Send the information to FacturaNotaComplementoService.
			comprobanteFiscal = comprobanteFiscalService.save(comprobante);

			/***************************
			 * Save XML into the server
			 */

			// Initialize folders and get the route.
			comprobanteStorageService.init(comprobanteFiscal);
			// Store the XML in the server.
			ruta = comprobanteStorageService.store(files.get(0), comprobanteFiscal);
			// Save document object.
			documentoService.save(comprobanteFiscal, "xml", "Documentos Fiscales", ruta);

			/*************************************
			 * Save PDF into the server
			 */

			if (files.size() > 1) {
				// Take the route.
				ruta = comprobanteStorageService.store(files.get(1), comprobanteFiscal);
				// Save document object.
				documentoService.save(comprobanteFiscal, "pdf", "Documentos Fiscales", ruta);
			}

			/***********************************************************
			 * Use InvoiceOne web service to validate and set responses.
			 *
			 */
			
			validationResponse = validationService.validaVerifica(files.get(0));
			
			comprobanteFiscalService.setValidation(comprobanteFiscal, validationResponse);
			
			// send email to the supplier, invoice received
			emailSender.sendEmailNewDoc(comprobanteFiscal, validationResponse.get(1), validationResponse.get(2));


		} catch (FileExistsException e) {
			// La factura ya existe
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			Log.activity("Error al intentar guardar factura: " + e.getMessage(), comprobante.getReceptorNombre());
			e.printStackTrace();
		} catch (NotFoundException e) {
			// La empresa o el proveedor no se encuentran en el catalogo
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			Log.activity("Error al intentar guardar factura: " + e.getMessage(), comprobante.getReceptorNombre());
			e.printStackTrace();
		} catch (XmlInfrastructureException e) {
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			Log.falla("Error al leer el CFD: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// Error inesperado
			files.forEach(file -> fileStorageService.migrateAttachments(file, "ERROR"));
			Log.activity("Error al intentar guardar factura: Ocurrió un error inesperado", comprobante.getReceptorNombre());
			e.printStackTrace();
		}
	}

	private void zipExtraction(BodyPart bodyPart) {
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

					// save the zip attachments
					fileStorageService.storeZipAttachment(targetStream, ze);

				}

				zis.closeEntry();
				ze = zis.getNextEntry();
			}

			// close last ZipEntry
			zis.closeEntry();
			zis.close();

		} catch (IOException | MessagingException e) {
			Log.falla("Error al procesar el archivo comprimido ZIP: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private List<BodyPart> getAllAttachments(Message[] messages) {

		// TODO try to reduce algorithm complexity
		
		List<BodyPart> bodyParts = new ArrayList<>();
		Message msg;
		Multipart multipart;
		BodyPart bodyPart;
		String fileName;
		String extension;
		

		for (int i = 0; i < messages.length; i++) {
			// assign first message
			msg = messages[i];

			try {
				
				// get message content
				multipart = (Multipart) msg.getContent();

				for (int j = 0; j < multipart.getCount(); j++) {

					bodyPart = multipart.getBodyPart(j);
					fileName = bodyPart.getFileName();
					extension = FilenameUtils.getExtension(fileName);

					// check if extension is not null, we make sure that a file exists.
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
				Log.falla("Ocurrió un error durante la lectura del mensaje de correo.");
				e.printStackTrace();
			} catch (IOException e) {
				Log.falla("No fué posible extraer el contenido del mensaje de correo.");
			}
		}

		return bodyParts;
	}

}
