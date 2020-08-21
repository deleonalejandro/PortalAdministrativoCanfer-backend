package com.canfer.app.cfd;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Concepto")
public class Concepto {
	
	private String claveProdServ;
	private String noIdentificacion;
	private String cantidad;
	private String claveUnidad;
	private String unidad;
	private String descripcion;
	private String valorUnitario;
	private String importe;
	
	public Concepto() {
		// Constructor vacio
	}
	
	public String getClaveProdServ() {
		return claveProdServ;
	}
	public String getNoIdentificacion() {
		return noIdentificacion;
	}
	public String getCantidad() {
		return cantidad;
	}
	public String getClaveUnidad() {
		return claveUnidad;
	}
	public String getUnidad() {
		return unidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getValorUnitario() {
		return valorUnitario;
	}
	public String getImporte() {
		return importe;
	}
	
	@XmlAttribute(name = "ClaveProdServ")
	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}
	
	@XmlAttribute(name = "NoIdentificacion")
	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}
	
	@XmlAttribute(name = "Cantidad")
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	@XmlAttribute(name = "ClaveUnidad")
	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}
	
	@XmlAttribute(name = "Unidad")
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	
	@XmlAttribute(name = "Descripcion")
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@XmlAttribute(name = "ValorUnitario")
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@XmlAttribute(name = "Importe")
	public void setImporte(String importe) {
		this.importe = importe;
	}

	@Override
	public String toString() {
		return "Concepto [claveProdServ=" + claveProdServ + ", noIdentificacion=" + noIdentificacion + ", cantidad="
				+ cantidad + ", claveUnidad=" + claveUnidad + ", unidad=" + unidad + ", descripcion=" + descripcion
				+ ", valorUnitario=" + valorUnitario + ", importe=" + importe + "]";
	}
	
	
	
	

}
