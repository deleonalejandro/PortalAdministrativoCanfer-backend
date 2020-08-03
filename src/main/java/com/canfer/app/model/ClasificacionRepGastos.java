package com.canfer.app.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	
@Entity(name = "ClasificacionRepGastos")
public class ClasificacionRepGastos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idClasificacionRepGastos; 
	
	@Column(nullable = false)
	private String clasificacion;

	public ClasificacionRepGastos() {
		super();
	}

	public long getIdClasificacionRepGastos() {
		return idClasificacionRepGastos;
	}

	public void setIdClasificacionRepGastos(long idClasificacionRepGastos) {
		this.idClasificacionRepGastos = idClasificacionRepGastos;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	} 
	
	
	
}

