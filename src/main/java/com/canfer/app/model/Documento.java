package com.canfer.app.model;


import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


import org.hibernate.annotations.CreationTimestamp;

	
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
	public void delete() {}
	public void download() {}
	
	
	
	
	
}
