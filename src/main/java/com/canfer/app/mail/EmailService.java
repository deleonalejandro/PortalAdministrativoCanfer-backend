package com.canfer.app.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.persistence.NoResultException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.Log;
import com.canfer.app.model.ModuleActions;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.StorageException;

@Service
public class EmailService {

	@Autowired
	private ComprobanteStorageService comprobanteStorageService;
	@Autowired
	private ModuleActions actioner;

	public EmailService() {
		// Constructor vacio
	}

	public void handleEmails(Message[] messages) {

		List<BodyPart> attachments;
		List<Archivo> files = new ArrayList<>();

		attachments = getAllAttachments(messages);

		if (attachments.isEmpty()) {
			throw new NoResultException("No existen archivos adjuntos.");
		}

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
						files.addAll(zipExtraction(attachment));
					}

					else {
						// Save the documents into the base folder
						files.add(comprobanteStorageService.storeAttachment(attachment));
					}

				}

			} catch (StorageException | MessagingException e) {
				Log.falla("Error al procesar correo: No fue posible recibir el archivo adjunto. " + e.getMessage(),
						"ERROR_FILE");
			}

		}

		if (processDocuments(files)) {
			Log.general("Los correos fueron procesados satisfactoriamente. La bandeja de entrada ha sido vaciada.");
		}

	}

	private boolean processDocuments(List<Archivo> files) {

		ArchivoPDF pdfFile = null;
		ArchivoXML xmlFile = null;
		List<Archivo> filesMatched = new ArrayList<>();
		List<ArchivoXML> filesXML = new ArrayList<>();

		for (Archivo file : files) {

			if (file instanceof ArchivoXML) {

				ArchivoXML xml = (ArchivoXML) file;
				filesXML.add(xml);

			}
		}

		for (Archivo file : filesXML) {

			String baseName = FilenameUtils.getBaseName(file.getNombre());

			filesMatched = files.stream().filter(f -> f.getNombre().contains(baseName))
						.collect(Collectors.toList());

			
			// Move files to corresponding official directories
			for (Archivo matchedFile : filesMatched) {

				if (matchedFile instanceof ArchivoPDF) {

					pdfFile = (ArchivoPDF) matchedFile;

				} else {

					xmlFile = (ArchivoXML) matchedFile;

				}

				// Erase files from the entries directory
				files.remove(matchedFile);

			}

			actioner.upload(xmlFile, pdfFile);

		}
		
		for (Archivo file : files) {
			
			file.discard();
		}
		
		return true; 
		
		
	}


	
	private List<Archivo> zipExtraction(BodyPart bodyPart) {
		
		List<Archivo> files = new ArrayList<>(); 
		
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
					files.add(comprobanteStorageService.storeZipAttachment(targetStream, ze));

				}

				zis.closeEntry();
				ze = zis.getNextEntry();
			}

			// close last ZipEntry
			zis.closeEntry();
			zis.close();

		} catch (IOException | MessagingException e) {
			
			Log.falla("Error al procesar el archivo comprimido ZIP: " + e.getMessage(), "ERROR_STORAGE");
			 
		}
		
		return files; 

	}

	private List<BodyPart> getAllAttachments(Message[] messages) {

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
				Log.falla("Ocurrió un error durante la lectura del mensaje de correo.", "ERROR_FILE");
				e.printStackTrace();
			} catch (IOException e) {
				Log.falla("No fué posible extraer el contenido del mensaje de correo.", "ERROR_FILE");
			}
		}

		return bodyParts;
	}

}
