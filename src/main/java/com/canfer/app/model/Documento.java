package com.canfer.app.model;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.canfer.app.repository.DocumentoRepository;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;


	
@Entity(name = "Documento")
public class Documento {
	
	@Transient
	@Autowired
	private DocumentoRepository docRepo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumento;
	
	@JoinColumn(name = "id_xml")
	@OneToOne(cascade = CascadeType.ALL)
	private ArchivoXML archivoXML;
	
	@JoinColumn(name = "id_pdf")
	@OneToOne(cascade = CascadeType.ALL)
	private ArchivoPDF archivoPDF;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime fechaCreacion;
	
	private LocalDateTime fechaMod;
	
	private String objEntry;
	
	private String docEntry;
	
	public Documento() {
	}
	

	//Constructor
	public Documento(ArchivoXML archivoXML, ArchivoPDF archivoPDF) {
		
		// adding company stamp to PDF file
		archivoPDF.setReceptor(archivoXML.getReceptor());

		this.archivoXML = archivoXML;
		this.archivoPDF = archivoPDF;
	}
	
	public Documento(ArchivoXML archivoXML) {
		
		this.archivoXML = archivoXML;
		
	}
	
	public Documento(ArchivoPDF archivoPDF) {
		
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

	public ArchivoXML getArchivoXML() {
		return archivoXML;
	}

	public void setArchivoXML(ArchivoXML archivoXML) {
		this.archivoXML = archivoXML;
	}

	public ArchivoPDF getArchivoPDF() {
		return archivoPDF;
	}

	public void setArchivoPDF(ArchivoPDF archivoPDF) {
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

	public boolean hasPDF() {
			
		return (this.archivoPDF != null); 
	
	}
	
	public boolean hasXML() {
		
		return (this.archivoXML != null);
			
	}
	
	public void accept(String nombre, String ruta) {
		
		if (this.hasXML()) {
		 this.archivoXML.accept(nombre, ruta);
		}
		
		if (this.hasPDF()) {
		 this.archivoPDF.accept(nombre, ruta);
		}	
	}
	
	public void delete() {
		
		//delete archivos
		if (this.hasXML()) {
			this.archivoXML.deleteFile();
		}	
		
		if (this.hasPDF()) {
			this.archivoPDF.deleteFile();
		}
			
		
	}
	
	public List<Resource> download() throws IOException {
		
		List<Resource> resources = new ArrayList<>();
		
		if (this.hasPDF()) {
			
			Resource resourcePDF = this.archivoPDF.loadAsResource();
			
			if (resourcePDF!= null) {
				
				resources.add(this.archivoPDF.loadAsResource());
				
			}
		}
			
		if (this.hasXML()) {
			
			Resource resourceXML = this.archivoPDF.loadAsResource();
			
			if (resourceXML!= null) {
				
				resources.add(this.archivoXML.loadAsResource());
				
			}
			
		}

		
		return resources; 
		
	}
	
	
	
}
