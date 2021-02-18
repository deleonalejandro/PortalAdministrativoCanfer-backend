package com.canfer.app.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


	
@Entity(name = "FormularioCajaChica")
public class FormularioCajaChica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFormularioCajaChica; 
	
    @JoinColumn(name = "SocioNegocio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursal;
	
	private Long folio;
	
	@CreationTimestamp
	private LocalDateTime fecha; 
	
	@UpdateTimestamp
	private LocalDateTime fechaMod;
	
	private String estatus; 
	
	private String responsable; 
	
	private String comentario; 
	
	private Float total;

	//Constructor
	public FormularioCajaChica(Sucursal sucursal, Long folio) {
		
		super(); 
		this.sucursal = sucursal;
		this.folio = folio;
		this.estatus = "ABIERTO";
		this.total = 0F;
	}
	
	public FormularioCajaChica() {
		super();
	}


	//Getters and Setters

	public long getIdFormularioCajaChica() {
		return idFormularioCajaChica;
	}

	public void setIdFormularioCajaChica(long idFormularioCajaChica) {
		this.idFormularioCajaChica = idFormularioCajaChica;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	
	public String getResponsable() {
		return responsable;
	}

	public void setProveedor(String responsable) {
		this.responsable = responsable;
	}

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	} 
	
	public boolean isOpen() {
		return this.estatus.equalsIgnoreCase("ABIERTO");
	}
	
	
	
}
