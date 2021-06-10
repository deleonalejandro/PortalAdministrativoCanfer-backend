package com.canfer.app.dto;

import java.util.Set;
import com.canfer.app.model.Usuario.UsuarioCanfer;

public class SucursalDTO {
	
	private Long idSucursal;
	private String empresa;
	private String proveedor;
	private String claveProv;
	private String nombreSucursal;
	private Set<UsuarioCanfer> usuariosCanfer;
	
	public SucursalDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getClaveProv() {
		return claveProv;
	}

	public void setClaveProv(String claveProv) {
		this.claveProv = claveProv;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public Set<UsuarioCanfer> getUsuariosCanfer() {
		return usuariosCanfer;
	}

	public void setUsuariosCanfer(Set<UsuarioCanfer> usuariosCanfer) {
		this.usuariosCanfer = usuariosCanfer;
	}
	
	

}
