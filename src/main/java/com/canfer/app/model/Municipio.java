package com.canfer.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


	
@Entity(name = "Municipio")
	
public class Municipio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id_Municipio; 
	
    @JoinColumn(name = "id_Estado")
    @ManyToOne(fetch = FetchType.LAZY)
    private Estado estado;
	
	@Column(nullable = false)
	private String nombre;

	@OneToMany(mappedBy = "municipio", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private Empresa empresa;

	//Constructor
	
	public Municipio(Estado estado, String nombre, Empresa empresa) {
		this.estado = estado;
		this.nombre = nombre;
		this.empresa = empresa;
	}
	
	//Getters and Setters

	public long getId_Municipio() {
		return id_Municipio;
	}

	public void setId_Municipio(long id_Municipio) {
		this.id_Municipio = id_Municipio;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
	
}
