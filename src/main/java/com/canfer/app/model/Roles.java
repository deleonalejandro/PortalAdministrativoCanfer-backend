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
	public Long idRoles; 
	
	@Column(nullable = false)
	private String rol;

	//Constructores
	public Roles() {
		//Constructor vacio
	}
	
	public Roles(String rol) {
		this.rol = rol;
	}
	

	//Getters and Setters

	public long getidRoles() {
		return idRoles;
	}

	public void setidRoles(Long idRoles) {
		this.idRoles = idRoles;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
}