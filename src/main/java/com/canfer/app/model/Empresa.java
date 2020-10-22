package com.canfer.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmpresa;
	
    private String municipio;
	
	@ManyToMany(mappedBy = "empresas")
	private List<Usuario> usuarios;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "empresas", cascade = CascadeType.REMOVE)
	private List<Proveedor> proveedores;
	
	@OneToMany(mappedBy = "empresa", cascade = CascadeType.REMOVE)
    private List<Consecutivo> consecutivos;
	
	@Column(nullable = false)
	private String rfc;
	
	@Column(nullable = true)
	private String color;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = true)
	private String calle;
	
	@Column(nullable = true)
	private String numExt;
	
	@Column(nullable = true)
	private String numInt;
	
	@Column(nullable = true)
	private String colonia;
	
	@Column(nullable = true)
	private String localidad;
	
	@Column(nullable = true)
	private String referencia;
	
	@Column(nullable = true)
	private String cp;
		
	@Column(nullable = true)
	private String contacto;
	
	@Column(nullable = true)
	private String correo;
	
	@Column(nullable = true)
	private String telefono;
	
	@Column(nullable = true)
	private String paginaWeb;
	
	@Column(nullable = true)
	private Long idUsuarioCreador;
	
	private String color;
	
	private String profilePictureName;

	//Constructor

	public Empresa(String nombre, String rfc) {
		this.nombre = nombre;
		this.rfc = rfc;
	}
	

	public Empresa() {
		super();
	}


	//Getters and Setters
	
	public Long getidEmpresa() {
		return idEmpresa;
	}

	public void setidEmpresa(long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


	public List<Proveedor> getProveedores() {
		return proveedores;
	}


	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}


	public Long getIdUsuarioCreador() {
		return idUsuarioCreador;
	}


	public void setIdUsuarioCreador(Long idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getProfilePictureName() {
		return profilePictureName;
	}


	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}
	
	
	
}
