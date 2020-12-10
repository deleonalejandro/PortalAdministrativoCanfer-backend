package com.canfer.app.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.canfer.app.model.ComprobanteFiscal.Factura;



	
@Entity(name = "PagoSAP")
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPago; 
	
	@Column
	private Integer idNumPago;
	
	@Column
	private Long idNumSap;
	
	@Column
	private String rfcEmpresa; 
	
	@Column
	private String claveProveedor;
	
	@Column
	private String rfcProveedor; 
	
	@Column
	private String correo; 

	@Column
	private Float totalParcialidad; 
	
	@Column
	private Float totalFactura; 
	
	@Column
	private String nuevoEstatusFactura; 
	
	@Column
	private String repBaseDatos; 
	
	@Column
	private Date fecMvto; 
	
	@Column
	private Boolean bitProcesado; 
	
	@Column
	private Boolean bitEnviarCorreo; 
	
	@Column 
	private Float totalPago; 
	
	@Column
	private String moneda;
	
	@JoinColumn(name = "idDocumento")
	@OneToOne(cascade = CascadeType.ALL)
	private Documento documento;

	//Constructor 
	public Pago() {
	}

	
	//Getters and Setters
	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	public Integer getIdNumPago() {
		return idNumPago;
	}

	public void setIdNumPago(Integer idNumPago) {
		this.idNumPago = idNumPago;
	}

	public Long getIdNumSap() {
		return idNumSap;
	}

	public void setIdNumSap(Long idNumSap) {
		this.idNumSap = idNumSap;
	}
	


	public String getRfcEmpresa() {
		return rfcEmpresa;
	}

	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}

	public String getClaveProveedor() {
		return claveProveedor;
	}

	public void setClaveProveedor(String claveProveedor) {
		this.claveProveedor = claveProveedor;
	}

	public String getRfcProveedor() {
		return rfcProveedor;
	}

	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}

	public Float getTotalParcialidad() {
		return totalParcialidad;
	}

	public void setTotalParcialidad(Float totalParcialidad) {
		this.totalParcialidad = totalParcialidad;
	}

	public Float getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(Float totalFactura) {
		this.totalFactura = totalFactura;
	}

	public String getNuevoEstatusFactura() {
		return nuevoEstatusFactura;
	}

	public void setNuevoEstatusFactura(String nuevoEstatusFactura) {
		this.nuevoEstatusFactura = nuevoEstatusFactura;
	}

	public String getRepBaseDatos() {
		return repBaseDatos;
	}

	public void setRepBaseDatos(String repBaseDatos) {
		this.repBaseDatos = repBaseDatos;
	}

	public Date getFecMvto() {
		return fecMvto;
	}

	public void setFecMvto(Date fecMvto) {
		this.fecMvto = fecMvto;
	}

	public Boolean getBitProcesado() {
		return bitProcesado;
	}

	public void setBitProcesado(Boolean bitProcesado) {
		this.bitProcesado = bitProcesado;
	}

	public Boolean getBitEnviarCorreo() {
		return bitEnviarCorreo;
	}

	public void setBitEnviarCorreo(Boolean bitEnviarCorreo) {
		this.bitEnviarCorreo = bitEnviarCorreo;
	}

	public Float getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(Float totalPago) {
		this.totalPago = totalPago;
	}

	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	} 
	
	
    
}