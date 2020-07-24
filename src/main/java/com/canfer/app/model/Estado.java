package com.canfer.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


	
@Entity(name = "Estado")
	
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id_Estado; 
	
	@Column(nullable = false)
	private String nombre;
	
	@OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Municipio municipio;

	//Constructor
	
	public Estado(String nombre, Municipio municipio) {
		this.nombre = nombre;
		this.municipio = municipio;
	}

	//Getters and Setters
	
	public long getId_Estado() {
		return id_Estado;
	}

	public void setId_Estado(long id_Estado) {
		this.id_Estado = id_Estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
	
	
}