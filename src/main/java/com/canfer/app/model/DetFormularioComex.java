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
public class DetFormularioComex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idDetFormularioComex; 
	
	@Column(nullable = false)
	private Long idDocumento; 
	
    @JoinColumn(name = "idFormulario")
    @ManyToOne(targetEntity = FormularioComex.class, fetch = FetchType.LAZY)
    private FormularioComex formularioComex;
	
	@JoinColumn(name= "clasificacion")
	@ManyToOne(targetEntity = ClasificacionComex.class, fetch = FetchType.LAZY)
	private ClasificacionComex clasificacion;
	
	 @JoinColumn(name = "Proveedor", referencedColumnName= "claveProv")
	    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
	    private Proveedor proveedor;
	 
	 //Constructor
	 
	 public DetFormularioComex() {
			super();
	 }

	public DetFormularioComex(Long idDocumento, FormularioComex formularioComex) {
		this.idDocumento = idDocumento;
		this.formularioComex = formularioComex;
	}

	//Getters and Setters 
	
	public long getIdDetFormularioComex() {
		return idDetFormularioComex;
	}

	public void setIdDetFormularioComex(long idDetFormularioComex) {
		this.idDetFormularioComex = idDetFormularioComex;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public FormularioComex getFormularioComex() {
		return formularioComex;
	}

	public void setFormularioComex(FormularioComex formularioComex) {
		this.formularioComex = formularioComex;
	}

	public ClasificacionComex getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(ClasificacionComex clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	 
	
	 
	 
	
}
