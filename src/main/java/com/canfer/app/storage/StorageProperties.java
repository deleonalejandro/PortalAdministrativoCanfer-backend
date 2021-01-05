package com.canfer.app.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.canfer.app.model.RutaAlmacenamiento;
import com.canfer.app.repository.RutaAlmacenamientoRepository;

@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
	
	@Autowired
	private RutaAlmacenamientoRepository rutasRepo;
	
	private RutaAlmacenamiento rutaObject;
	
	public StorageProperties() {
		// default constructor
	}
	
	public Path getFacturasLocation() {
		
		this.rutaObject = rutasRepo.findByDescripcion("facturas");
		
		return Paths.get(this.rutaObject.getRuta());
	}
	
	public void setFacturasLocation(Path facturasLocation) {
		
		this.rutaObject = rutasRepo.findByDescripcion("facturas");
		this.rutaObject.setRuta(facturasLocation.toString());
		
		rutasRepo.save(this.rutaObject);
		
	}
	
	public Path getEntriesLocation() {
		
		this.rutaObject = rutasRepo.findByDescripcion("entries");
		
		return Paths.get(this.rutaObject.getRuta());
	}
	
	public void setEntriesLocation(Path entriesLocation) {

		this.rutaObject = rutasRepo.findByDescripcion("entries");
		this.rutaObject.setRuta(entriesLocation.toString());
		
		rutasRepo.save(this.rutaObject);
	}
	
	
	public Path getErrorLocation() {
		
		this.rutaObject = rutasRepo.findByDescripcion("error");
		
		return Paths.get(this.rutaObject.getRuta());
	}
	
	public void setErrorLocation(Path errorLocation) {
		
		this.rutaObject = rutasRepo.findByDescripcion("error");
		this.rutaObject.setRuta(errorLocation.toString());
		
		rutasRepo.save(this.rutaObject);
		
	}

	public Path getLogoLocation() {
		
		this.rutaObject = rutasRepo.findByDescripcion("logo");
		
		return Paths.get(this.rutaObject.getRuta());
	}
	
	public void setLogoLocation(Path logoLocation) {
		
		this.rutaObject = rutasRepo.findByDescripcion("logo");
		this.rutaObject.setRuta(logoLocation.toString());
		
		rutasRepo.save(this.rutaObject);
	}
	
	public Path getEntryPortalLocation() {
		
		this.rutaObject = rutasRepo.findByDescripcion("entry");
		
		return Paths.get(this.rutaObject.getRuta());
	}
	
	public void setEntryPortalLocation(Path entryPortalLocation) {
		
		this.rutaObject = rutasRepo.findByDescripcion("entry");
		this.rutaObject.setRuta(entryPortalLocation.toString());
		
		rutasRepo.save(this.rutaObject);
	}
	
	public Path getLogLocation() {
		
		this.rutaObject = rutasRepo.findByDescripcion("log");
		
		return Paths.get(this.rutaObject.getRuta());
		
	}
	
	public void setLogLocation(Path logLocation) {
		
		this.rutaObject = rutasRepo.findByDescripcion("log");
		this.rutaObject.setRuta(logLocation.toString());
		
		rutasRepo.save(this.rutaObject);
	}
	
	

	
	
	
	
	
	
}
