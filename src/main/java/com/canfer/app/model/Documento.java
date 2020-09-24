package com.canfer.app.model;


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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

	
@Entity(name = "Documento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Documento")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumento;
	
	private Long idTabla;
	
	@JoinColumn(name = "idEmpresa")
	@ManyToOne
	private Empresa empresa;
	
	private String objEntry;
	
	private String docEntry;
	
	@Column(nullable = false)
	private String modulo;
	
	@Column(nullable = false)
	private String concepto;
	
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime fecha;
	
	@Column(nullable = false)
	private String extension;
	
	@Column(nullable = false)
	private String ruta;

	//Constructor
	
	public Documento() {
		super();
	}
	
	public Documento(Long idTabla, Empresa empresa, String modulo, String concepto, String extension, String ruta) {
		super();
		this.idTabla = idTabla;
		this.empresa = empresa;
		this.modulo = modulo;
		this.concepto = concepto;
		this.extension = extension;
		this.ruta = ruta;
	}


	//Getters and Setters

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Long getIdTabla() {
		return idTabla;
	}

	public void setIdTabla(Long idTabla) {
		this.idTabla = idTabla;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	
	@Entity
	@DiscriminatorValue("DOCUMENTO_XML")
	public static class DocumentoXML extends Documento {
		
		public DocumentoXML() {
			// Default constructor
		}
		
		public DocumentoXML(Long idTabla, Empresa empresa, String modulo, String concepto, String extension, String ruta) {
			super(idTabla, empresa, modulo, concepto, extension, ruta);
		}
	}
	
	@Entity
	@DiscriminatorValue("DOCUMENTO_PDF")
	public static class DocumentoPDF extends Documento {
		
		public DocumentoPDF() {
			// Default constructor
		}
		
		public DocumentoPDF(Long idTabla, Empresa empresa, String modulo, String concepto, String extension, String ruta) {
			super(idTabla, empresa, modulo, concepto, extension, ruta);
		}
	}
	
	
	
	
	
	
}
