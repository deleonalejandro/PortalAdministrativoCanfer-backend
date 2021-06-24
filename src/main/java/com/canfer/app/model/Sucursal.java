package com.canfer.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "sucursal")
public class Sucursal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSucursal;
	
	@JsonIgnore
	@JoinColumn(name = "idEmpresa")
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa empresa;
	
	@JsonIgnore
	@JoinColumn(name = "idProveedor")
	@OneToOne
	private Proveedor proveedor;
	
	@OneToMany(mappedBy = "sucursal", cascade = CascadeType.REMOVE)
    private List<Consecutivo> consecutivos;
	
	
	private String claveProv;
	
	private String nombreSucursal;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "sucursal_usuario",
			joinColumns = @JoinColumn(name = "id_sucursal"),
			inverseJoinColumns = @JoinColumn(name = "id_usuario_canfer")
			)
	private Set<UsuarioCanfer> usuariosCanfer;
	
	private String empresaRfc;
	
	private String proveedorRfc;
	
	public Sucursal() {
		// TODO Auto-generated constructor stub
	}
	
	public Sucursal(Empresa empresa, Proveedor proveedor) {
		
		this.empresa = empresa;
		this.proveedor = proveedor;
		this.claveProv = proveedor.getClaveProv();
		this.nombreSucursal = proveedor.getNombre();
		this.empresaRfc = empresa.getRfc();
		this.proveedorRfc = proveedor.getRfc();
		
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

	public String getClaveProv() {
		return claveProv;
	}

	public void setClaveProv(String claveProv) {
		this.claveProv = claveProv;
	}

	public Set<UsuarioCanfer> getUsuariosCanfer() {
		return usuariosCanfer;
	}

	public void setUsuariosCanfer(Set<UsuarioCanfer> usuariosCanfer) {
		this.usuariosCanfer = usuariosCanfer;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public String getEmpresaRfc() {
		return empresaRfc;
	}

	public void setEmpresaRfc(String empresaRfc) {
		this.empresaRfc = empresaRfc;
	}

	public String getProveedorRfc() {
		return proveedorRfc;
	}

	public void setProveedorRfc(String proveedorRfc) {
		this.proveedorRfc = proveedorRfc;
	}
	
	public String getEmpresaNombre() {
		return this.empresa.getNombre();
	}
	
	public List<String> getUsers(){
		List<String> userNames = new ArrayList<>();
		this.usuariosCanfer.forEach(user -> userNames.add(user.getNombre() + " " + user.getApellido()));
		return userNames;
	}
	
	public List<String> getUsersAndId(){
		List<String> userNames = new ArrayList<>();
		this.usuariosCanfer.forEach(user -> userNames.add(String.valueOf(user.getIdUsuario())+"-"+user.getNombre() + " " + user.getApellido()));
		return userNames;
	}
	
	public void updateRfc() {
		this.proveedorRfc = proveedor.getRfc();
		this.empresaRfc = empresa.getRfc();
	}
	
	public Boolean addUser(UsuarioCanfer newUser) {
		if (!this.usuariosCanfer.contains(newUser)) {
			this.usuariosCanfer.add(newUser);
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	

}
