package com.canfer.app.dto;

import java.util.List;

public class ProveedorDTO {
	private Long idProveedor = 0L;
	private String municipio;
	private Long idMunicipio;
	private String rfc;
	private String nombre;
	private String calle;
	private String numExt;
	private String numInt;
	private String colonia;
	private String localidad;
	private String referencia;
	private String cp;
	private String contacto;
	private String correo;
	private String telefono;
	private String paginaWeb;
	private Long idUsuarioCreador;
	
	//additional attributes 
	private Long idEmpresa;
	private String claveProv;
	private String moneda;
	private String serie;
	private Boolean bitActivo;
	
	public ProveedorDTO() {
		// General constructor, no parameters.
	}

	
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


	public Long getIdMunicipio() {
		return idMunicipio;
	}


	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
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

	public Long getIdUsuarioCreador() {
		return idUsuarioCreador;
	}

	public void setIdUsuarioCreador(Long idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
	}


	public Long getIdEmpresa() {
		return idEmpresa;
	}


	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}


	public String getClaveProv() {
		return claveProv;
	}


	public void setClaveProv(String claveProv) {
		this.claveProv = claveProv;
	}


	public String getMoneda() {
		return moneda;
	}


	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}


	public String getSerie() {
		return serie;
	}


	public void setSerie(String serie) {
		this.serie = serie;
	}


	public Boolean getBitActivo() {
		return bitActivo;
	}


	public void setBitActivo(Boolean bitActivo) {
		this.bitActivo = bitActivo;
	}
	
	
	
	
}

