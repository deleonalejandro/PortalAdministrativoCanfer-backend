package com.canfer.app.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import javax.mail.BodyPart;
import javax.mail.MessagingException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.model.Log;


@Service
public class ComprobanteStorageService implements StorageService {
	
	@Autowired
	private EmpresaRepository empresaRepository;


	private Path rootLocation;
	private Path entriesPortalLocation;
	private Path entriesEmailLocation;
	

	public ComprobanteStorageService(StorageProperties storageProperties) {
		this.rootLocation = storageProperties.getFacturasLocation();
		this.entriesPortalLocation = storageProperties.getEntryPortalLocation();
		this.entriesEmailLocation = storageProperties.getEntriesLocation();
	}

	@Override
	public void store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Error al guardar un archivo vacío. " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
		}
	}
	
	@Override
	public void init() {
		try {
			
			Files.createDirectories(this.rootLocation);
			Files.createDirectories(this.entriesEmailLocation);
			Files.createDirectories(this.entriesPortalLocation);
			
		} catch (IOException e) {
			
			Log.falla("No fué posible inicializar los directorios.", "ERROR_STORAGE");
		}
	}
	
	public String init(Path ruta) {

		try {

			Files.createDirectories(this.rootLocation.resolve(ruta));

		} catch (IOException e) {

			Log.falla("No fué posible inicializar los directorios.", "ERROR_STORAGE");

		}

		return String.valueOf(this.rootLocation.resolve(ruta));
	}

	public Archivo storePortalFile(MultipartFile multipartFile) {

		String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Path fileLocation = this.entriesPortalLocation.resolve(filename);
		String extension = FilenameUtils.getExtension(filename);
		
		try {

			if (multipartFile.isEmpty()) {

				throw new StorageException("Error al guardar un archivo vacío. " + filename);

			}

			if (fileLocation.toString().contains("..")) {

				// This is a security check
				throw new StorageException("No es posible guardar un archivo con una ruta relativa fuera del directorio.");

			}

			try (InputStream inputStream = multipartFile.getInputStream()) {

				Files.copy(inputStream, fileLocation, StandardCopyOption.REPLACE_EXISTING);

			}

		}

		catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
		}

		if(extension.equalsIgnoreCase("pdf")) {
			
			return new ArchivoPDF(fileLocation.toString() , "pdf" , filename);
		
		} else {
			
			return new ArchivoXML(fileLocation.toString() , "xml" , filename);
		}
	}

	public Archivo storeAttachment(BodyPart bodyPart) throws MessagingException {
		
		String filename = null; 
		filename = bodyPart.getFileName();
		Path fileLocation = this.entriesEmailLocation.resolve(filename);
		String extension = FilenameUtils.getExtension(filename);
		
		try {


			if (filename.contains("..")) {

				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}

			try (InputStream inputStream = bodyPart.getInputStream()) {

				Files.copy(inputStream, fileLocation, StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (IOException e) {
			
			throw new StorageException("Error al guardar el archivo " + filename, e);
			
		} catch (MessagingException e) {
			
			throw new MessagingException("Ocurrio un error con el archivo adjunto " + filename, e);
			
		}
		
		if(extension.equalsIgnoreCase("pdf")) {
	
			return new ArchivoPDF(fileLocation.toString() , "pdf" , filename);
		
		} else {
			
			return new ArchivoXML(fileLocation.toString() , "xml" , filename);
		}
			
	}
	
	public Archivo storeZipAttachment(InputStream is, ZipEntry ze) throws MessagingException {
		
		String filename = ze.getName();
		Path fileLocation = this.entriesEmailLocation.resolve(filename);
		String extension = FilenameUtils.getExtension(filename);

		try {
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}

			Files.copy(is, fileLocation, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			
			throw new StorageException("Error al guardar el archivo " + filename, e);
		}
		
		if (extension.equalsIgnoreCase("pdf")) {
			
			return new ArchivoPDF(fileLocation.toString() , "pdf" , filename);
		
		} else {
			
			return new ArchivoXML(fileLocation.toString() , "xml" , filename);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		return null;
	}

	@Override
	public Path load(String filename) {
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		return null;
	}

	@Override
	public void deleteAll() {
		
	}

	

}
