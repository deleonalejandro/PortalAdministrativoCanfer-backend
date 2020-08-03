package com.canfer.app.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	
@Entity(name = "Clasificacion_CajaChica")
public class ClasificacionCajaChica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idClasificacionCajaChica; 
	
	@Column(nullable = false)
	private String clasificacion;
	
	//Constructor

	public ClasificacionCajaChica() {
		super();
	}

	//Getters and Setters
	
	public long getIdClasificacionCajaChica() {
		return idClasificacionCajaChica;
	}

	public void setIdClasificacionCajaChica(long idClasificacionCajaChica) {
		this.idClasificacionCajaChica = idClasificacionCajaChica;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	} 
	
	
	
}

