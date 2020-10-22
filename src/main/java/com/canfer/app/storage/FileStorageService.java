package com.canfer.app.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import javax.mail.BodyPart;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements StorageService {

	private final Path rootLocation;
	private final Path rootLocationError;
	private final Path rootLocationUnknown;

	@Autowired
	public FileStorageService(StorageProperties properties) {
		this.rootLocation = properties.getEntriesLocation();
		this.rootLocationError = properties.getErrorLocation();
		this.rootLocationUnknown = properties.getDownloadLocation();
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
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Error al leer los archivos alamacenados", e);
		}

	}
	
	public Stream<Path> loadAllRoutes() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation));
		} catch (IOException e) {
			throw new StorageException("Error al leer los archivos alamacenados", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("No se pudo leer el archivo: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("No se pudo leer el archivo: " + filename, e);
		}
	}
	

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			Files.createDirectories(rootLocationError);
			Files.createDirectories(rootLocationUnknown);
		} catch (IOException e) {
			throw new StorageException("No fue posible inicializar los directorios.", e);
		}
	}

	public void storeAttachment(BodyPart bodyPart) throws MessagingException {
		String filename = null;
		try {
			filename = bodyPart.getFileName();
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}
			try (InputStream inputStream = bodyPart.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
		} catch (MessagingException e) {
			throw new MessagingException("Ocurrio un error con el archivo adjunto " + filename, e);
		}
	}

	public void storeZipAttachment(InputStream is, ZipEntry ze) throws MessagingException {
		String filename = ze.getName();
		try {
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}

			Files.copy(is, this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
		}
	}

	public void migrateAttachments(Path path, String folder) {
		
		File file = path.toFile();
		
		try(InputStream is = new FileInputStream(file)) { 
			
			
			if (folder.equalsIgnoreCase("ERROR")) {
				//saving 
				Files.copy(is, this.rootLocationError.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			} else {
				//saving 
				Files.copy(is, this.rootLocationUnknown.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			}
			
		} catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + path.getFileName(), e);
		}
		
					

	}

}
