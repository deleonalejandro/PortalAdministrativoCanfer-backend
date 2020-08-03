package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DetFormularioRepGastos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDetFormularioRepGastos; 
	
	@Column(nullable = false)
	private Long idDocumento; 
	
    @JoinColumn(name = "idFormulario")
    @ManyToOne(targetEntity = FormularioRepGastos.class, fetch = FetchType.LAZY)
    private FormularioRepGastos formularioRepGastos;
	
	@JoinColumn(name= "clasificacion")
	@ManyToOne(targetEntity = ClasificacionRepGastos.class, fetch = FetchType.LAZY)
	private ClasificacionRepGastos clasificacion;
	
	@Column(nullable = true)
	private Float importe; 
	
	@Column(nullable = true)
	private Float iva; 
	
	@Column(nullable = false)
	private Float total; 
	
	@Column(nullable = false)
	private String nombreProveedor; 
	
	@Column(nullable = false)
	private String fecha; 
	
	@Column(nullable = false)
	private String responsable; 
	
	@Column(nullable = false)
	private String folio;
	
	@Column(nullable = true)
	private Float propina; 

	//Constructor
	
	public DetFormularioRepGastos() {
		super();
	}
	
	public DetFormularioRepGastos(FormularioRepGastos formularioRepGastos, String folio) {
		this.formularioRepGastos = formularioRepGastos;
		this.folio = folio;
	}

	//Getters and Setters
	
	public long getIdDetFormularioRepGastos() {
		return idDetFormularioRepGastos;
	}

	public void setIdDetFormularioRepGastos(long idDetFormularioRepGastos) {
		this.idDetFormularioRepGastos = idDetFormularioRepGastos;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public FormularioRepGastos getFormularioRepGastos() {
		return formularioRepGastos;
	}

	public void setFormularioRepGastos(FormularioRepGastos formularioRepGastos) {
		this.formularioRepGastos = formularioRepGastos;
	}

	public ClasificacionRepGastos getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(ClasificacionRepGastos clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public Float getIva() {
		return iva;
	}

	public void setIva(Float iva) {
		this.iva = iva;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
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

	public Float getPropina() {
		return propina;
	}

	public void setPropina(Float propina) {
		this.propina = propina;
	}
	
	
}
