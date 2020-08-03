package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	
@Entity(name = "TipoReposicion")
public class TipoReposicion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idTipoReposicion; 
	
	@Column(nullable = false)
	private String tipo;

	public TipoReposicion() {
		super();
	}

	public long getIdTipoReposicion() {
		return idTipoReposicion;
	}

	public void setIdTipoReposicion(long idTipoReposicion) {
		this.idTipoReposicion = idTipoReposicion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	} 
	
	
	
}

