package com.canfer.app.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


	
@Entity(name = "Estado")
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEstado; 
	
	@Column(nullable = false)
	private String nombre;

	//Constructor
	
	public Estado(String nombre) {
		this.nombre = nombre;
	}

	public Estado() {
		super();
	}

	//Getters and Setters
	
	public long getidEstado() {
		return idEstado;
	}

	public void setidEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}