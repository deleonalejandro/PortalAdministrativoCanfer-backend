package com.canfer.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "RutaDeAlmacenamiento")
public class RutaAlmacenamiento {
	
	@Id
	@GeneratedValue
	private Long idRutaAlmacenamiento;
	
	@Column
	private String ruta;
	
	@Column
	private String descripcion;
	
	@Column
	private boolean activa;
	
	@Column
	@CreationTimestamp
	private LocalDateTime fecha;
	
	@Column
	@UpdateTimestamp
	private LocalDateTime fechaMov;
	
	public RutaAlmacenamiento() {
		// default constructor
	}
	
	public RutaAlmacenamiento(String ruta, String descripcion, boolean activa) {
		
		this.ruta = ruta;
		this.descripcion = descripcion;
		this.activa = activa;
				
	}
	

	public Long getIdRutaAlmacenamiento() {
		return idRutaAlmacenamiento;
	}

	public void setIdRutaAlmacenamiento(Long idRutaAlmacenamiento) {
		this.idRutaAlmacenamiento = idRutaAlmacenamiento;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public LocalDateTime getFechaMov() {
		return fechaMov;
	}

	public void setFechaMov(LocalDateTime fechaMov) {
		this.fechaMov = fechaMov;
	}
	
	

}
