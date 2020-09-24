package com.canfer.app.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.stream.Stream;


import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.ComprobanteFiscal;


@Service
public class ComprobanteStorageService implements StorageService {

	private Path rootLocation;
	
	public ComprobanteStorageService(StorageProperties storageProperties) {
		this.rootLocation = storageProperties.getFacturasLocation();
	}
	
	@Override
	public void store(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
	}

	public String store(MultipartFile file, ComprobanteFiscal comprobante) {
		
		Path filename = getFilename(comprobante, FilenameUtils.getExtension(file.getOriginalFilename()));
		try {
			if (filename.toString().contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory "
								+ filename);
			}
			
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
		
		return this.rootLocation.resolve(filename).toString();
	}
	
	public String store(Path path, ComprobanteFiscal comprobante) {
		
		Path filename = getFilename(comprobante, FilenameUtils.getExtension(path.getFileName().toString()));
		try {
			
			//Make a file from the given path
			File file = new File(path.toString());
			InputStream is = new FileInputStream(file);
			
			Files.copy(is, this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			// closing the input stream 
			is.close();
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}
		
		return this.rootLocation.resolve(filename).toString();
	}
	
	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
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
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}
	
	public Resource loadAsResource(Path file) {
		try {
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + file.getFileName());

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + file.getFileName(), e);
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
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
	
	public void init(ComprobanteFiscal comprobante) {
		LocalDateTime today = LocalDateTime.now();
		this.rootLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Facturas", "PortalProveedores", 
				comprobante.getEmpresa().getRfc(), 
				String.valueOf(today.getYear()),
				String.valueOf(today.getMonthValue()), 
				comprobante.getProveedor().getRfc());
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
	
	private Path getFilename(ComprobanteFiscal comprobante, String extension) {
		
		String folioFinal;
		
		if (comprobante.getFolio() == null) {
			folioFinal = String.valueOf(comprobante.getIdNumSap());
		} else {
			folioFinal = comprobante.getFolio();
		}
		
		if (comprobante.getSerie() == null) {
			return Paths.get(comprobante.getProveedor().getRfc() + "_" + folioFinal + "."
					+ extension);
		} else {
			return Paths.get(comprobante.getProveedor().getRfc() + "_" + comprobante.getSerie() + "_" + folioFinal + "."
					+ extension);
		}
	}
	
}
