package com.canfer.app.model;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.io.Resource;

	
@Entity(name = "Documento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Documento")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumento;
	
	private Archivo archivoXML;
	
	private Archivo archivoPDF;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime fechaCreacion;
	
	private LocalDateTime fechaMod;
	
	private String objEntry;
	
	private String docEntry;
	
	

	//Constructor
	
	public Documento() {
		
	}
	
	public Documento(Archivo archivoXML, Archivo archivoPDF) {
		this.archivoXML = archivoXML;
		this.archivoPDF = archivoPDF;
			
	}


	//Getters and Setters
	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getObjEntry() {
		return objEntry;
	}

	public void setObjEntry(String objEntry) {
		this.objEntry = objEntry;
	}

	public String getDocEntry() {
		return docEntry;
	}

	public void setDocEntry(String docEntry) {
		this.docEntry = docEntry;
	}

	public Archivo getArchivoXML() {
		return archivoXML;
	}

	public void setArchivoXML(Archivo archivoXML) {
		this.archivoXML = archivoXML;
	}

	public Archivo getArchivoPDF() {
		return archivoPDF;
	}

	public void setArchivoPDF(Archivo archivoPDF) {
		this.archivoPDF = archivoPDF;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaMod() {
		return fechaMod;
	}

	public void setFechaMod(LocalDateTime fechaMod) {
		this.fechaMod = fechaMod;
	}
	
	//TODO 
	public void save() {}
	
	public void delete() {
		
		//delete archivos
		this.archivoPDF.deleteFile();
		this.archivoXML.deleteFile();
		
		
	}
	public List<Resource> download() throws IOException {
		
		List<Resource> resources = new ArrayList<>();
		
		if (this.archivoPDF != null) {
			
			Resource resourcePDF = this.archivoPDF.loadAsResource();
			
			if (resourcePDF!= null) {
				
				resources.add(this.archivoPDF.loadAsResource());
				
			}
		}
			
		if (this.archivoXML != null) {
			
			Resource resourceXML = this.archivoPDF.loadAsResource();
			
			if (resourceXML!= null) {
				
				resources.add(this.archivoXML.loadAsResource());
				
			}
			
		}

		
		return resources; 
		
	}
	
	
	
	
	
}
