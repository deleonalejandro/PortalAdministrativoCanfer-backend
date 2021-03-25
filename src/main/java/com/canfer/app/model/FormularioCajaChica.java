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

import com.fasterxml.jackson.annotation.JsonIgnore;


	
@Entity(name = "FormularioCajaChica")
public class FormularioCajaChica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFormularioCajaChica; 
	
	@JsonIgnore
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
	
	private String paqueteria;
	
	private String numeroGuia;
	
	private String numeroPago;
	
	private LocalDateTime fechaPago;

	//Constructor
	public FormularioCajaChica(Sucursal sucursal, Long folio, String responsable) {
		
		super(); 
		this.sucursal = sucursal;
		this.folio = folio;
		this.estatus = "ABIERTO";
		this.total = 0F;
		this.responsable = responsable;
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

	public LocalDateTime getFechaMod() {
		return fechaMod;
	}

	public void setFechaMod(LocalDateTime fechaMod) {
		this.fechaMod = fechaMod;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
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

	public String getPaqueteria() {
		return paqueteria;
	}

	public void setPaqueteria(String paqueteria) {
		this.paqueteria = paqueteria;
	}

	public String getNumeroGuia() {
		return numeroGuia;
	}

	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}
	
	public String getNumeroPago() {
		return numeroPago;
	}

	public void setNumeroPago(String numeroPago) {
		this.numeroPago = numeroPago;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public boolean isOpen() {
		return this.estatus.equalsIgnoreCase("ABIERTO");
	}

	public String getNombreSucursal() {
		return this.sucursal.getNombreSucursal();
	}
	
	public String getClaveProvSucursal() {
		return this.sucursal.getClaveProv();
	}
	
	
	
	
}
