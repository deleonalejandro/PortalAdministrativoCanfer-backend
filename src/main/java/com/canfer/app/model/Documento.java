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
	private Archivo archivoXML;
	
	@JoinColumn(name = "id_pdf")
	@OneToOne(cascade = CascadeType.ALL)
	private Archivo archivoPDF;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime fechaCreacion;
	
	private LocalDateTime fechaMod;
	
	private String objEntry;
	
	private String docEntry;
	
	

	//Constructor
	public Documento(Archivo archivoXML, Archivo archivoPDF) {
		
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
		return (ArchivoXML) archivoXML;
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
	public void save() {
		
		this.archivoXML.save();
		this.archivoPDF.save();
		
		docRepo.save(this);
		
		
	}
	
	public void accept(String nombre, String ruta) {
		
		this.archivoXML.accept(nombre, ruta);
		
		this.archivoPDF.accept(nombre, ruta);
		
	}
	
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
