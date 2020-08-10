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



	
@Entity(name = "FormularioRepGastos")
public class FormularioRepGastos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idFormularioRepGastos; 
	
    @JoinColumn(name = "SocioNegocio", referencedColumnName= "claveProv")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    private Proveedor proveedor;
	
    @JoinColumn(name= "TipoReposicion")
	@ManyToOne(targetEntity = TipoReposicion.class, fetch = FetchType.LAZY)
	private TipoReposicion clasificacion;
    
	@Column(nullable = false)
	private Integer folio;
	
	@Column(nullable = true)
	private String fechaAnticipo; 
	
	@Column(nullable = true)
	private String tipoAnticipo;
	
	@Column(nullable = true)
	private String empresaAnticipo;
	
	@Column(nullable = true)
	private String sobranteAnticipo;
	
	@Column(nullable = false)
	private Boolean bitViaje; 
	
	@Column(nullable = true)
	private String periodoViaje; 
	
	@Column(nullable = true)
	private String DestinoViaje; 
	
	@Column(nullable = false)
	private String fecha; 
	
	@Column(nullable = false)
	private String estatus; 
	
	@Column(nullable = true)
	private String comentario; 
	
	@Column(nullable = false)
	private Float total;

	//Constructor
	
	
	public FormularioRepGastos(Proveedor proveedor, Integer folio, String fecha, String estatus) {
		
		super();
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date); 
		
		this.proveedor = proveedor;
		this.folio = folio;
		this.fecha = strDate;
		this.estatus = "Abierto";
	}
	
	public FormularioRepGastos() {
		super();
	}

	//Getters and Setters
	

	public long getIdFormularioRepGastos() {
		return idFormularioRepGastos;
	}

	public void setIdFormularioRepGastos(long idFormularioRepGastos) {
		this.idFormularioRepGastos = idFormularioRepGastos;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public TipoReposicion getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(TipoReposicion clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	public String getFechaAnticipo() {
		return fechaAnticipo;
	}

	public void setFechaAnticipo(String fechaAnticipo) {
		this.fechaAnticipo = fechaAnticipo;
	}

	public String getTipoAnticipo() {
		return tipoAnticipo;
	}

	public void setTipoAnticipo(String tipoAnticipo) {
		this.tipoAnticipo = tipoAnticipo;
	}

	public String getEmpresaAnticipo() {
		return empresaAnticipo;
	}

	public void setEmpresaAnticipo(String empresaAnticipo) {
		this.empresaAnticipo = empresaAnticipo;
	}

	public String getSobranteAnticipo() {
		return sobranteAnticipo;
	}

	public void setSobranteAnticipo(String sobranteAnticipo) {
		this.sobranteAnticipo = sobranteAnticipo;
	}

	public Boolean getBitViaje() {
		return bitViaje;
	}

	public void setBitViaje(Boolean bitViaje) {
		this.bitViaje = bitViaje;
	}

	public String getPeriodoViaje() {
		return periodoViaje;
	}

	public void setPeriodoViaje(String periodoViaje) {
		this.periodoViaje = periodoViaje;
	}

	public String getDestinoViaje() {
		return DestinoViaje;
	}

	public void setDestinoViaje(String destinoViaje) {
		DestinoViaje = destinoViaje;
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
