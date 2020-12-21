package com.canfer.app.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.validator.PublicClassValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "Usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Usuario")
public abstract class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@JoinTable(
			name = "usuario_empresa",
			joinColumns = @JoinColumn(name="idUsuario"),
			inverseJoinColumns = @JoinColumn(name="idEmpresa")
			)
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Empresa> empresas;
	
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
	
	@JsonIgnore
	public List<String> getPermisosList(){
		if (this.permisos.isEmpty()) {
			return new ArrayList<>();
		}
		
		return Arrays.asList(this.permisos.split(",")); 
	}

	public List<Long> getEmpresasId() {
		List<Long> ids = new ArrayList<>();
		for (Empresa company : this.empresas) {
			ids.add(company.getidEmpresa());
		}
		return ids;
	}
	
	public List<String> getEmpresasNombre() {
		List<String> names = new ArrayList<>();
		for (Empresa company : this.empresas) {
			names.add(company.getNombre());
		}
		return names;
	}
	
	public List<String> getEmpresasRfc() {
		List<String> rfc = new ArrayList<>();
		for (Empresa company : this.empresas) {
			rfc.add(company.getRfc());
		}
		return rfc;
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
	
	public String getType() {
		if (this instanceof UsuarioCanfer) {
			return "CANFER";
		} else {
			return "PROVEEDOR";
		}
	}
	
	@Entity
	@DiscriminatorValue("USUARIO_CANFER")
	public static class UsuarioCanfer extends Usuario {
		
		public UsuarioCanfer(String username, String password, String nombre, String apellido, String correo, String rol, String permisos) {
			// use the super class constructor
			super(username, password, nombre, apellido, correo, rol, permisos);
		}
		
		public UsuarioCanfer() {
			// default constructor
		}
		
	}

	@Entity
	@DiscriminatorValue("USUARIO_PROVEEDOR")
	public static class UsuarioProveedor extends Usuario {
		
		@JoinTable( 
		        name = "usuario_proveedor",
		        joinColumns = @JoinColumn(name = "id_usuario_proveedor", nullable = false),
		        inverseJoinColumns = @JoinColumn(name="id_proveedor", nullable = false)
			    )
		@ManyToMany(fetch = FetchType.LAZY)
		private List<Proveedor> proveedores;
		
		
		public UsuarioProveedor(String username, String password, String nombre, String apellido, String correo, String rol, String permisos) {
			super(username, password, nombre, apellido, correo, rol, permisos);
		}
		
		public UsuarioProveedor() {
			// default constructor
		}


		public List<Proveedor> getProveedores() {
			return proveedores;
		}

		public void setProveedores(List<Proveedor> proveedores) {
			this.proveedores = proveedores;
		}
		
		
		
	}
	

}
