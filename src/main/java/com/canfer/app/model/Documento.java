package com.canfer.app.model;


import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

	
@Entity(name = "Documento")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumento; 
	
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
	
	public Documento(String modulo, String concepto, String extension, String ruta) {
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
	
	
	
	
}
