package com.canfer.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "sucursal")
public class Sucursal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSucursal;
	
	@JoinColumn(name = "idEmpresa")
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	@JoinColumn(name = "idProveedor")
	@OneToOne
	private Proveedor proveedor;
	
	private String claveProv;
	
	public Sucursal() {
		// TODO Auto-generated constructor stub
	}
	
	public Sucursal(Empresa empresa, Proveedor proveedor) {
		
		this.empresa = empresa;
		this.proveedor = proveedor;
		this.claveProv = proveedor.getClaveProv();
		
	}

	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	

}
