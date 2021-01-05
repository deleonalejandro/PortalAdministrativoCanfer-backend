package com.canfer.app.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.Pago;

@Service
public class PaymentStorageService implements StorageService {
	
	@Autowired
	private StorageProperties storageProperties;
	
	private Path rootLocation;
	
	@Autowired
	public PaymentStorageService(StorageProperties storageProperties) {
		this.rootLocation = storageProperties.getPaymentLocation();
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
				throw new StorageException("No es posible guardar un archivo con una ruta relativa fuera del directorio " + filename);
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
			
		} catch (IOException e) {
			
			throw new StorageException("No fue posible inicializar los directorios.", e);
		}
	}
	
	public Path init(Pago pago) {
		
		LocalDateTime today = LocalDateTime.now();
		
		Path route = Paths.get(pago.getRfcEmpresa(), 
				String.valueOf(today.getYear()),
				String.valueOf(today.getMonthValue()), 
				pago.getRfcProveedor());
		try {
			
			
			Files.createDirectories(rootLocation.resolve(route));
			
					
		} catch (IOException e) {
			
			throw new StorageException("No fue posible inicializar los directorios: " + route);
		}
		
		return rootLocation.resolve(route);
		
	}
	
	public boolean updatePaths() {
		
		this.rootLocation = storageProperties.getPaymentLocation();
		
		return true;
		
	}

}
