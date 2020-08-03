package com.canfer.app.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	
@Entity(name = "ClasificacionComex")
public class ClasificacionComex {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idClasificacionComex; 
	
	@Column(nullable = false)
	private String clasificacion;

	public ClasificacionComex() {
		super();
	}

	public long getIdClasificacionComex() {
		return idClasificacionComex;
	}

	public void setIdClasificacionComex(long idClasificacionComex) {
		this.idClasificacionComex = idClasificacionComex;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	} 
	
	
	
}

