package com.canfer.app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


	
@Entity(name = "FormularioCajaChica")
public class FormularioCajaChica {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFormularioCajaChica; 
	
    @JoinColumn(name = "SocioNegocio", referencedColumnName= "claveProv")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    private Proveedor proveedor;
	
	@Column(nullable = false)
	private Integer folio;
	
	@Column(nullable = false)
	private String fecha; 
	
	@Column(nullable = false)
	private String estatus; 
	
	@Column(nullable = true)
	private String comentario; 
	
	@Column(nullable = false)
	private Float total;

	//Constructor
	public FormularioCajaChica(Proveedor proveedor, Integer folio, String fecha, String estatus) {
		
		super();
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date); 
		
		this.proveedor = proveedor;
		this.folio = folio;
		this.fecha = strDate;
		this.estatus = "Abierto";
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

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
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
	
	
	
}
