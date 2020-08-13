package com.canfer.app.model;


import java.util.ArrayList;
import java.util.Arrays;
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
import javax.persistence.ManyToOne;


@Entity(name = "Usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@JoinTable(
			name = "usuario_empresa",
			joinColumns = @JoinColumn(name="idUsuario", nullable = false),
			inverseJoinColumns = @JoinColumn(name="idEmpresa", nullable = false)
			)
	@ManyToMany
	private List<Empresa> empresas;
	
	@JoinColumn(name = "idProveedor")
	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Proveedor proveedor;
	
	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellido;
	
	@Column(nullable = false)
	private String correo;
	
	@Column(nullable = false)
	private String rol;
	
	@Column(nullable = false)
	private String permisos = "";
	
	private Boolean activo;
	
	//Main constructor from the class Usuario.
	public Usuario(String username, String password, String nombre, String apellido, String correo, String rol, String permisos) {
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.rol = rol;
		this.permisos = permisos;
		this.activo = true;
	}
	
	public Usuario() {
		super();
	}

	//We create the getters and setters from the class
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPermisos() {
		return permisos;
	}

	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}
	
	public List<String> getPermisosList(){
		if (this.permisos.isEmpty()) {
			return new ArrayList<>();
		}
		
		return Arrays.asList(this.permisos.split(",")); 
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}
	
	public void addEmpresa(Empresa empresa) {
		//If the list is null, we create a new list.
		if (this.empresas.isEmpty()) {
			this.empresas = new ArrayList<>();
		}
		this.empresas.add(empresa);
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	

}
