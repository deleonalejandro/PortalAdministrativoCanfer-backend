package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "Empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id_Empresa;
	
	@JoinColumn(name = "id_Municipio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Municipio municipio;
	
	@Column(nullable = false)
	private String rfc;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String calle;
	
	@Column(nullable = false)
	private String numExt;
	
	@Column(nullable = false)
	private String numInt;
	
	@Column(nullable = false)
	private String colonia;
	
	@Column(nullable = false)
	private String localidad;
	
	@Column(nullable = false)
	private String referencia;
	
	@Column(nullable = false)
	private String cp;
	
	@Column(nullable = false)
	private Boolean bit_Activo;	
	
	@Column(nullable = false)
	private String contacto;
	
	@Column(nullable = false)
	private String correo;
	
	@Column(nullable = false)
	private String telefono;
	
	@Column(nullable = false)
	private String paginaWeb;
	
	@Column(nullable = false)
	private long id_UsuarioCreador;

	//Constructor
	
	public Empresa(Municipio municipio, String rfc, String nombre, String calle, String numExt, String numInt,
			String colonia, String localidad, String referencia, String cp, Boolean bit_Activo, String contacto,
			String correo, String telefono, String paginaWeb, long id_UsuarioCreador) {
		this.municipio = municipio;
		this.rfc = rfc;
		this.nombre = nombre;
		this.calle = calle;
		this.numExt = numExt;
		this.numInt = numInt;
		this.colonia = colonia;
		this.localidad = localidad;
		this.referencia = referencia;
		this.cp = cp;
		this.bit_Activo = bit_Activo;
		this.contacto = contacto;
		this.correo = correo;
		this.telefono = telefono;
		this.paginaWeb = paginaWeb;
		this.id_UsuarioCreador = id_UsuarioCreador;
	}

	//Getters and Setters
	
	public long getId_Empresa() {
		return id_Empresa;
	}

	public void setId_Empresa(long id_Empresa) {
		this.id_Empresa = id_Empresa;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumExt() {
		return numExt;
	}

	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}

	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public Boolean getBit_Activo() {
		return bit_Activo;
	}

	public void setBit_Activo(Boolean bit_Activo) {
		this.bit_Activo = bit_Activo;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public long getId_UsuarioCreador() {
		return id_UsuarioCreador;
	}

	public void setId_UsuarioCreador(long id_UsuarioCreador) {
		this.id_UsuarioCreador = id_UsuarioCreador;
	}

	
	
}
