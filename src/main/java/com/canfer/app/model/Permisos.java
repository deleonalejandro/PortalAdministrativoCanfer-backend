package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



	
@Entity(name = "Permisos")
	
public class Permisos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long idPermisos; 
	
	@Column(nullable = false)
	private String permiso;


	//Constructores
	
	public Permisos() {
		//Constructor vacio
	}
	
	public Permisos(String permiso) {
		this.permiso = permiso;
	}

	//Getters and Setters
	
	public long getidPermisos() {
		return idPermisos;
	}

	public void setidPermisos(long idPermisos) {
		this.idPermisos = idPermisos;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
	
	
}