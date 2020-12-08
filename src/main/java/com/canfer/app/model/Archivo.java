package com.canfer.app.model;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.canfer.app.storage.StorageFileNotFoundException;

@Entity(name = "Archivo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Archivo")
public class Archivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idArchivo; 
	
	@Column
	private String ruta;
	
	@Column
	private String extension;
	
	@Column
	private String nombre;
	
	@Column
	@CreationTimestamp
	private LocalDateTime fechaCarga;
	
	@Column
	private LocalDateTime fechaMod;
	
	public Resource loadAsResource() {
		
		try {
			
			Path file = Paths.get(this.ruta);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("No se pudo leer el archivo: " + nombre);

			}
		}
		
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("No se pudo leer el archivo: " + nombre);
		}
		
	}
	
 	public void deleteFile() {
 		
 		
 	}
 	public void createName() {}
 	public void move() {}
 	public void accept() {}
 	public void discard() {}
	
	@Entity
	@DiscriminatorValue("ARCHIVO_XML")
	public static class ArchivoXML extends Archivo {
		
		@Column
		private String uuid;
		
		@Column
		private String tipoComprobante;
		
		public ArchivoXML() {
			// Default constructor
		}
		
		public ComprobanteFiscal toCfdi() {
			return null;
			} 
		
		public String  toString() {
			return null;
			}
		
		public void businessValidation() {}
	}
	@Entity
	@DiscriminatorValue("ARCHIVO_PDF")
	public static class ArchivoPDF extends Archivo {
		public ArchivoPDF() {
			// Default constructor
		}
	}
}