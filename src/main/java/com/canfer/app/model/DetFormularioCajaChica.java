package com.canfer.app.model;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "DetFormularioCajaChica")
public class DetFormularioCajaChica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDetFormularioCajaChica; 
	
	@JsonIgnore
	@JoinColumn(name = "idDocumento")
	@OneToOne
	private Documento documento; 
	
	@JsonIgnore
    @JoinColumn(name = "idFormulario")
    @ManyToOne(targetEntity = FormularioCajaChica.class, fetch = FetchType.LAZY)
    private FormularioCajaChica formularioCajaChica;
	
	@JsonIgnore
	@JoinColumn(name= "clasificacion")
	@ManyToOne(targetEntity = ClasificacionCajaChica.class, fetch = FetchType.LAZY)
	private ClasificacionCajaChica clasificacion;
	
	private Float monto; 
	
	private LocalDateTime fecha; 
	
	@UpdateTimestamp
	private LocalDateTime fechaMod;
	
	private String beneficiario; 
	
	private String folio;
	
	private String nombreProveedor;
	
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

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
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

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	
	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String responsable) {
		this.beneficiario = responsable;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	} 
	
	public String getNombreArchivoXML() {
		if (this.documento.hasXML()) {			
			return this.documento.getArchivoXML().getNombre();
		}
		return null;
	}
	
	public String getNombreArchivoPDF() {
		if (this.documento.hasPDF()) {
			return this.documento.getArchivoPDF().getNombre();			
		}
		return null;
	}
	
	public String getNombreClasificacion() {
		return this.clasificacion.getClasificacion();
	}
	
	
}
