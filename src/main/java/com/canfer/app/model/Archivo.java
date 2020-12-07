package com.canfer.app.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.apache.commons.io.input.BOMInputStream;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.boot.jaxb.internal.stax.XmlInfrastructureException;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.storage.StorageFileNotFoundException;

@Entity(name = "Archivo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Archivo")
public class Archivo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idArchivo; 
	
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
	public void actualizar() {}
 	public void deleteFile() {}
 	public void createName() {}
 	public void move() {}
 	public void accept() {}
 	public void discard() {}
 	
 	
	
	public long getIdArchivo() {
		return idArchivo;
	}
	public void setIdArchivo(long idArchivo) {
		this.idArchivo = idArchivo;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDateTime getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(LocalDateTime fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public LocalDateTime getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(LocalDateTime fechaMod) {
		this.fechaMod = fechaMod;
	}



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
		
		public Comprobante toCfdi() {
			
			//Get ruta to path, get file from path
			Path path = Paths.get(this.getRuta()); 
			File file = path.toAbsolutePath().toFile();
			
			JAXBContext context;
			BOMInputStream bis;

			try (InputStream in = new FileInputStream(file)) {
				bis = new BOMInputStream(in);
				context = JAXBContext.newInstance(Comprobante.class);
				return (Comprobante) context.createUnmarshaller()
						.unmarshal(new InputStreamReader(new BufferedInputStream(bis)));
			} catch (JAXBException | IOException e) {
				e.printStackTrace();
				throw new XmlInfrastructureException("No fue posible leer el comprobante fiscal digital: " + this.getNombre());
			} 
		} 
		
		public String  toString() {
			
			// Get the file from the path and return string
			
			//Get ruta to path, get file from path
			Path path = Paths.get(this.getRuta()); 
			File file = path.toAbsolutePath().toFile();

			try (InputStream is = new FileInputStream(file);
					BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line).append("\n");
					line = br.readLine();
				}

				return sb.toString();

			} catch (Exception e) {
				e.printStackTrace();
				throw new XmlInfrastructureException("No fue posible leer el documento: " + this.getNombre());
			}

		}
		
		public void businessValidation() {
			
			Comprobante comprobante = this.toCfdi();
			
			if (exist(comprobante.getUuidTfd())) {
				throw new FileExistsException("El comprobante fiscal ya se encuentra registrado en la base de datos. UUID: "
						+ comprobante.getUuidTfd() + " Emisor: " + comprobante.getEmisor());
			}
			
			receptor = empresaRepository.findByRfc(comprobante.getReceptorRfc());
			proveedores = proveedorRepository.findAllByEmpresasAndRfc(receptor, comprobante.getEmisorRfc());
			// check if the company or the provider exist on the data base.
			if (receptor == null) {
				throw new NotFoundException("La empresa o el proveedor no estan registrados en el catalogo. "
						+ "Nombre Empresa: " + comprobante.getReceptorNombre() + " Empresa RFC: " + comprobante.getReceptorRfc() + "."); 
			}
			// get the proper provider
			if (proveedores.size() > 1 || proveedores.isEmpty()) {
				// more than one found in the query for PROVEEDOR, use PROVEEDOR GENERICO instead.
				emisor = proveedorRepository.findByEmpresasAndNombre(receptor, "PROVEEDOR GENÉRICO");
			} else {
				emisor = proveedores.get(0);
			}
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getTipoComprobante() {
			return tipoComprobante;
		}

		public void setTipoComprobante(String tipoComprobante) {
			this.tipoComprobante = tipoComprobante;
		}
		
		
		
	}
	@Entity
	@DiscriminatorValue("ARCHIVO_PDF")
	public static class ArchivoPDF extends Archivo {
		public ArchivoPDF() {
			// Default constructor
		}
	}
}