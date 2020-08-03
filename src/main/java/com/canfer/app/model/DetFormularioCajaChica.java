package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "DetFormularioCajaChica")
public class DetFormularioCajaChica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDetFormularioCajaChica; 
	
	@Column(nullable = false)
	private Long idDocumento; 
	
    @JoinColumn(name = "idFormulario")
    @ManyToOne(targetEntity = FormularioCajaChica.class, fetch = FetchType.LAZY)
    private FormularioCajaChica formularioCajaChica;
	
	@JoinColumn(name= "clasificacion")
	@ManyToOne(targetEntity = ClasificacionCajaChica.class, fetch = FetchType.LAZY)
	private ClasificacionCajaChica clasificacion;
	
	@Column(nullable = false)
	private Float monto; 
	
	@Column(nullable = false)
	private String fecha; 
	
	@Column(nullable = false)
	private String responsable; 
	
	@Column(nullable = false)
	private String folio;

	//Constructor
	public DetFormularioCajaChica() {
		super();
	}
	
	public DetFormularioCajaChica(FormularioCajaChica formularioCajaChica, String folio) {
		this.formularioCajaChica = formularioCajaChica;
		this.folio = folio;
	}

	//Getters and Setters
	
	public long getIdDetFormularioCajaChica() {
		return idDetFormularioCajaChica;
	}

	public void setIdDetFormularioCajaChica(long idDetFormularioCajaChica) {
		this.idDetFormularioCajaChica = idDetFormularioCajaChica;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public FormularioCajaChica getFormularioCajaChica() {
		return formularioCajaChica;
	}

	public void setFormularioCajaChica(FormularioCajaChica formularioCajaChica) {
		this.formularioCajaChica = formularioCajaChica;
	}

	public ClasificacionCajaChica getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(ClasificacionCajaChica clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	} 
	
	
	
}
