package com.canfer.app.model;


import java.util.List;

public class UserDTO {
	
	private String username;
	private String password;
	private String nombre;
	private String apellido;
	private String correo;
	private Boolean activo;
	private String rol;
	private List <Long> permisos;
	
	//We make a default constructor
	public UserDTO() {
		// Constructor solo
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

	public List<Long> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Long> permisos) {
		this.permisos = permisos;
	}
}
	

	