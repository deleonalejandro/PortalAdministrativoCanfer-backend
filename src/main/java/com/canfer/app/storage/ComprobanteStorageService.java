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

import com.canfer.app.cfd.Comprobante;
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
				throw new StorageException("Error al guardar un archivo vacío. " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
		}
	}

	public String store(MultipartFile file, ComprobanteFiscal comprobante) {
		
		Path filename = getFilename(comprobante, FilenameUtils.getExtension(file.getOriginalFilename()));
		try {
			if (filename.toString().contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}
			
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
		}
		
		return this.rootLocation.resolve(filename).toString();
	}
	
	public String store(MultipartFile file, Comprobante comprobante, Long numSap) {
		
		Path filename = getFilename(comprobante, FilenameUtils.getExtension(file.getOriginalFilename()), numSap);
		try {
			if (filename.toString().contains("..")) {
				// This is a security check
				throw new StorageException(
						"No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
			}
			
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			throw new StorageException("Error al guardar el archivo " + filename, e);
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
	
	public String store(Path path, Comprobante comprobante, Long numSap) {
		
		Path filename = getFilename(comprobante, FilenameUtils.getExtension(path.getFileName().toString()), numSap);
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
			}
			else {
				throw new StorageFileNotFoundException("No se pudo leer el archivo: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("No se pudo leer el archivo: " + filename);
		}
	}
	
	public Resource loadAsResource(Path file) {
		try {
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("No se pudo leer el archivo: " + file.getFileName());

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("No se pudo leer el archivo: " + file.getFileName());
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
			throw new StorageException("No fué posible inicializar los directorios.", e);
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
			throw new StorageException("No fué posible inicializar los directorios.", e);
		}
	}
	
	public void init(Comprobante comprobante) {
		LocalDateTime today = LocalDateTime.now();
		this.rootLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Facturas", "PortalProveedores", 
				comprobante.getReceptorRfc(), 
				String.valueOf(today.getYear()),
				String.valueOf(today.getMonthValue()), 
				comprobante.getEmisorRfc());
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("No fué posible inicializar los directorios.", e);
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
	
	private Path getFilename(Comprobante comprobante, String extension, Long numSap) {
		
		String folioFinal;
		
		if (comprobante.getFolio() == null) {
			folioFinal = String.valueOf(numSap);
		} else {
			folioFinal = comprobante.getFolio();
		}
		
		if (comprobante.getSerie() == null) {
			return Paths.get(comprobante.getEmisorRfc() + "_" + folioFinal + "."
					+ extension);
		} else {
			return Paths.get(comprobante.getEmisorRfc() + "_" + comprobante.getSerie() + "_" + folioFinal + "."
					+ extension);
		}
	}
	
}
