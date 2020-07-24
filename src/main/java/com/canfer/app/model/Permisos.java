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
	private long id_Permisos; 
	
	@Column(nullable = false)
	private String permiso;

	//Constructor
	
	public Permisos(String permiso) {
		this.permiso = permiso;
	}

	//Getters and Setters
	
	public long getId_Permisos() {
		return id_Permisos;
	}

	public void setId_Permisos(long id_Permisos) {
		this.id_Permisos = id_Permisos;
	}

	public String getPermiso() {
		return permiso;
	}

	public void setPermiso(String permiso) {
		this.permiso = permiso;
	}
	
	
	
}