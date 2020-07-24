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
	private long id_Roles; 
	
	@Column(nullable = false)
	private String rol;

	//Constructor
	
	public Roles(String rol) {
		this.rol = rol;
	}
	

	//Getters and Setters

	public long getId_Roles() {
		return id_Roles;
	}

	public void setId_Roles(long id_Roles) {
		this.id_Roles = id_Roles;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}