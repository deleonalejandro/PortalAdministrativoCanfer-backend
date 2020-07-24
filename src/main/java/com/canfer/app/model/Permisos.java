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
	private long Id_Permisos; 
	
	@Column(nullable = false)
	private String Permiso;
	
	
	// We create the constructor
	
	public Permisos(String Permiso) {
		this.Permiso = Permiso;
	}

	// Getters and Setters
	
	public long getId_Permisos() {
		return Id_Permisos;
	}

	public void setId_Permisos(long id_Permisos) {
		Id_Permisos = id_Permisos;
	}

	public String getPermiso() {
		return Permiso;
	}

	public void setPermiso(String rol) {
		Permiso = rol;
	}
	
	
}