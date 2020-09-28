package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPago;
	
	@Column(nullable = false)
	private String idNumPago;
	
	@Column
	private String idNumSap;
	
	@Column 
	private String rfcEmpresa; 
	
	@Column
	private String claveProveedor; 
	
	@Column 
	private String rfcProveedor; 
		
	@Column
	private String correo; 
	
	@Column
	private int totalParcialidad; 
	
	@Column
	private int totalFactura;
	
	@Column
	private String nuevoEstatusFactura; 
	
	@Column
	private String repBaseDatos; 
	
	@Column
	private String fecha; 
	
	@Column
	private Boolean bitProcesado; 
	
	@Column
	private Boolean bitEnviarCorreo; 
	
	@Column
	private int totalPago; 
	
	@Column
	private String moneda;

	public Pago() {
		super();
	}

	public long getIdPago() {
		return idPago;
	}

	public void setIdPago(long idPago) {
		this.idPago = idPago;
	}

	public String getIdNumPago() {
		return idNumPago;
	}

	public void setIdNumPago(String idNumPago) {
		this.idNumPago = idNumPago;
	}

	public String getIdNumSap() {
		return idNumSap;
	}

	public void setIdNumSap(String idNumSap) {
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getTotalParcialidad() {
		return totalParcialidad;
	}

	public void setTotalParcialidad(int totalParcialidad) {
		this.totalParcialidad = totalParcialidad;
	}

	public int getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(int totalFactura) {
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public int getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(int totalPago) {
		this.totalPago = totalPago;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	} 
	
	
}
