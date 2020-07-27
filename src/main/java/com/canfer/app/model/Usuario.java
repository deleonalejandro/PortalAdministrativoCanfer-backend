package com.canfer.app.model;


import java.util.ArrayList;
import java.util.List;

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

@Entity(name = "Usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUsuario;
	
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellido;
	
	@Column(nullable = false)
	private String correo;
	
	private Boolean activo;
	
	@JoinColumn(name = "idRoles")
	@ManyToOne(targetEntity = Roles.class, fetch = FetchType.LAZY)
	private Roles rol;
	
	//Relacion con permisos
	@OneToMany(targetEntity = Permisos.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List <Permisos> permisos = new ArrayList<>();
	
	//We create the constructor
	public Usuario(String username, String password, String nombre, String apellido, String correo) {
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.activo = true;
	}
	
	public Usuario() {
		super();
	}

	//We create the getters and setters from the class

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

	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}

	public List<Permisos> getPermisos() {
		if (this.permisos.isEmpty()) {
			return new ArrayList<>();
			
		}
		return permisos;
	}

	public void setPermisos(List<Permisos> permisos) {
		this.permisos = permisos;
	}
	
	
	
}
