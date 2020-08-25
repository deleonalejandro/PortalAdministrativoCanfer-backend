package com.canfer.app.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.FacturaNotaComplemento;


@Service
public class FacturaStorageService implements StorageService {

	private final Path rootLocation =  Paths.get(System.getProperty("user.home"), "PortalProveedores", "Facturas", "PortalProveedores");

	@Override
	public void store(MultipartFile file, Path filename) {
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

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public Path init(FacturaNotaComplemento factura) {
		LocalDateTime today = LocalDateTime.now();
		Path foldersPath = Paths.get(factura.getEmpresa().getRfc(), String.valueOf(today.getYear()),
				String.valueOf(today.getMonthValue()), factura.getProveedor().getRfc());
		try {
			Files.createDirectories(rootLocation.resolve(foldersPath));
			return foldersPath;
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
	
	public Path getFilename(FacturaNotaComplemento factura, Path foldersPath, String extension) {
		
		String folioFinal;
		
		if (factura.getFolio() == null) {
			folioFinal = String.valueOf(factura.getIdNumSap());
		} else {
			folioFinal = factura.getFolio();
		}
		
		if (factura.getSerie() == null) {
			return Paths.get(foldersPath.toString(), factura.getProveedor().getRfc() + "_" + folioFinal + "."
					+ extension);
		} else {
			return Paths.get(foldersPath.toString(), factura.getProveedor().getRfc() + "_" + factura.getSerie() + "_" + folioFinal + "."
					+ extension);
		}
	}
	
}
