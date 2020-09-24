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


	
@Entity(name = "FormularioComex")
public class FormularioComex {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFormularioComex; 
	
    @JoinColumn(name = "AgenciaAduanal")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    private Proveedor proveedor;
    
    @JoinColumn(name = "Empresa")
    @ManyToOne(targetEntity = Empresa.class, fetch = FetchType.LAZY)
    private Empresa empresa;
    
    @Column(nullable = false)
	private String tipoOperacion; 
	
	@Column(nullable = false)
	private Integer folio;
	
	@Column(nullable = false)
	private String fechaCaptura; 
	
	@Column(nullable = false)
	private String trafico; 
	
	@Column(nullable = false)
	private String fechaPedimento; 
	
	@Column(nullable = false)
	private String noPrecioEntrega; 
	
	@Column(nullable = false)
	private String noPedimento; 
	
	@Column(nullable = false)
	private String estatus; 
	
	@Column(nullable = true)
	private String comentario; 
	
	@Column(nullable = false)
	private Float tipoCambio;
	
	//Constructor
	
	
	
	public FormularioComex(Empresa empresa, Integer folio, String fechaCaptura, String estatus) {
	
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date); 
		
		this.empresa = empresa;
		this.folio = folio;
		this.fechaCaptura = strDate;
		this.estatus = "Abierto";
	}
	
	public FormularioComex() {
		super();
	}

	//Getters and Setters

	public long getIdFormularioComex() {
		return idFormularioComex;
	}

	public void setIdFormularioComex(long idFormularioComex) {
		this.idFormularioComex = idFormularioComex;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public String getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(String fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public String getTrafico() {
		return trafico;
	}

	public void setTrafico(String trafico) {
		this.trafico = trafico;
	}

	public String getFechaPedimento() {
		return fechaPedimento;
	}

	public void setFechaPedimento(String fechaPedimento) {
		this.fechaPedimento = fechaPedimento;
	}

	public String getNoPrecioEntrega() {
		return noPrecioEntrega;
	}

	public void setNoPrecioEntrega(String noPrecioEntrega) {
		this.noPrecioEntrega = noPrecioEntrega;
	}

	public String getNoPedimento() {
		return noPedimento;
	}

	public void setNoPedimento(String noPedimento) {
		this.noPedimento = noPedimento;
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

	public Float getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Float tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	
	
	
	
}
