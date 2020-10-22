package com.canfer.app.model;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



	
@Entity(name = "Municipio")
public class Municipio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idMunicipio; 
	
	@JsonIgnore
    @JoinColumn(name = "idEstado")
    @ManyToOne(targetEntity = Estado.class, fetch = FetchType.LAZY)
    private Estado estado;
	
	@Column(nullable = false)
	private String nombre;

	//Constructor
	
	
	public Municipio(String nombre) {
		this.nombre = nombre;
	}
	
	public Municipio() {
		super();
	}

	//Getters and Setters

	public long getidMunicipio() {
		return idMunicipio;
	}

	public void setidMunicipio(long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
