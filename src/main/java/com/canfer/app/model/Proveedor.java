package com.canfer.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "Proveedor")
public class Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProveedor;

	private String municipio;

	@ManyToMany(mappedBy = "proveedores")
	private List<Empresa> empresas;

	@Column(nullable = false)
	private String claveProv;

	@Column(nullable = false)
	private String rfc;

	@Column(nullable = true)
	private String serie;

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

	@Column(nullable = false)
	private Boolean bitActivo;

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

	@Column(nullable = true)
	private String moneda;

	// Constructor
	public Proveedor(Long idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
		this.bitActivo = true;
	}
	
	public Proveedor(String rfc) {
		this.rfc = rfc;
		this.claveProv = "GENERICO";
		this.nombre = "PROVEEDOR GENERICO";
		this.bitActivo = true;
	}

	public Proveedor() {
		super();
	}

	// Getters and Setters

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getClaveProv() {
		return claveProv;
	}

	public void setClaveProv(String claveProv) {
		this.claveProv = claveProv;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
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

	public Boolean getBitActivo() {
		return bitActivo;
	}

	public void setBitActivo(Boolean bitActivo) {
		this.bitActivo = bitActivo;
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

	public Long getIdUsuarioCreador() {
		return idUsuarioCreador;
	}

	public void setIdUsuarioCreador(Long idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	

}