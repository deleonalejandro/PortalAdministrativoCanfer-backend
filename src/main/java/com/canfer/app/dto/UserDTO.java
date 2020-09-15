package com.canfer.app.dto;


import java.util.ArrayList;
import java.util.List;

public class UserDTO {
	
	private Long userId;
	private String username;
	private String password;
	private String confirmPassword;
	private String nombre;
	private String apellido;
	private String correo;
	private Boolean activo;
	private String rol;
	private List <String> permisos = new ArrayList<>();
	private List<Long> empresaIdsList = new ArrayList<>();
	//El usuario es un proveedor
	private String rfc;
	private Long empresaCreadoraId;
	
	//We make a default constructor
	public UserDTO() {
		// Constructor solo
	}
	
	
	
	public Long getEmpresaCreadoraId() {
		return empresaCreadoraId;
	}

	public void setEmpresaCreadoraId(Long empresaCreadoraId) {
		this.empresaCreadoraId = empresaCreadoraId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public List<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<String> permisos) {
		this.permisos = permisos;
	}
	
	public String getPermisosToString() {
		if (this.permisos.isEmpty()) {
			return "Ningun permiso";
		}
		return String.join(",", this.permisos);
	}

	public List<Long> getEmpresaIdsList() {
		return empresaIdsList;
	}

	public void setEmpresaIdsList(List<Long> empresaIdsList) {
		this.empresaIdsList = empresaIdsList;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	
	
	
}
	

	