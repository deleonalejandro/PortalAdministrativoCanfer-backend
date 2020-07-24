package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	
@Entity(name = "Roles")
	
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id_Roles; 
	
	@Column(nullable = false)
	private String Rol;
	
	
	// We create the constructor
	
	public Roles(String Rol) {
		this.Rol = Rol;
	}

	// Getters and Setters
	
	public long getId_Roles() {
		return Id_Roles;
	}

	public void setId_Roles(long id_Roles) {
		Id_Roles = id_Roles;
	}

	public String getRol() {
		return Rol;
	}

	public void setRol(String rol) {
		Rol = rol;
	}
	
	
}