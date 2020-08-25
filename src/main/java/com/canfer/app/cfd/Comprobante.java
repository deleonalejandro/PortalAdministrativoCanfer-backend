package com.canfer.app.cfd;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.sat.gob.mx/cfd/3", name = "Comprobante")
public class Comprobante {
	
	private Emisor emisor;
	private Receptor receptor;
	private Complemento complemento;
	private Conceptos conceptos;
	private CfdiRelacionados cfdiRelacionados;
	
	private String version;
	private String serie;
	private String folio;
	private String fecha;
	private String sello;
	private String formaPago;
	private String noCertificado;
	private String certificado;
	private String condicionesDePago;
	private String subTotal;
	private String moneda;
	private String tipoCambio;
	private String total;
	private String tipoDeComprobante;
	private String metodoPago;
	private String lugarExpedicion;
	
	public Comprobante() {
		// Constructor vacio
	}

	public String getVersion() {
		return version;
	}

	public String getSerie() {
		return serie;
	}

	public String getFolio() {
		return folio;
	}

	public String getFecha() {
		return fecha;
	}

	public String getSello() {
		return sello;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public String getNoCertificado() {
		return noCertificado;
	}

	public String getCertificado() {
		return certificado;
	}

	public String getCondicionesDePago() {
		return condicionesDePago;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public String getMoneda() {
		return moneda;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public String getTotal() {
		return total;
	}

	public String getTipoDeComprobante() {
		return tipoDeComprobante;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public String getLugarExpedicion() {
		return lugarExpedicion;
	}
	
	public Emisor getEmisor() {
		return emisor;
	}

	public Receptor getReceptor() {
		return receptor;
	}
	
	public Complemento getComplemento() {
		return complemento;
	}
	
	public Conceptos getConceptos() {
		return conceptos;
	}
	
	
	public CfdiRelacionados getCfdiRelacionados() {
		return cfdiRelacionados;
	}

	@XmlElement(name = "CfdiRelacionados")
	public void setCfdiRelacionados(CfdiRelacionados cfdiRelacionados) {
		this.cfdiRelacionados = cfdiRelacionados;
	}

	@XmlElement(name = "Conceptos")
	public void setConceptos(Conceptos conceptos) {
		this.conceptos = conceptos;
	}

	@XmlAttribute(name = "Version")
	public void setVersion(String version) {
		this.version = version;
	}
	
	@XmlAttribute(name = "Serie")
	public void setSerie(String serie) {
		this.serie = serie;
	}

	@XmlAttribute(name = "Folio")
	public void setFolio(String folio) {
		this.folio = folio;
	}

	@XmlAttribute(name = "Fecha")
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@XmlAttribute(name = "Sello")
	public void setSello(String sello) {
		this.sello = sello;
	}

	@XmlAttribute(name = "FormaPago")
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	@XmlAttribute(name = "NoCertificado")
	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}

	@XmlAttribute(name = "Certificado")
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	@XmlAttribute(name = "CondicionesDePago")
	public void setCondicionesDePago(String condicionesDePago) {
		this.condicionesDePago = condicionesDePago;
	}

	@XmlAttribute(name = "SubTotal")
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	@XmlAttribute(name = "Moneda")
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@XmlAttribute(name = "TipoCambio")
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	@XmlAttribute(name = "Total")
	public void setTotal(String total) {
		this.total = total;
	}

	@XmlAttribute(name = "TipoDeComprobante")
	public void setTipoDeComprobante(String tipoDeComprobante) {
		this.tipoDeComprobante = tipoDeComprobante;
	}

	@XmlAttribute(name = "MetodoPago")
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	@XmlAttribute(name = "LugarExpedicion")
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	@XmlElement(name = "Emisor")
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}
	
	@XmlElement(name = "Receptor")
	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
	}
	
	@XmlElement(name = "Complemento")
	public void setComplemento(Complemento complemento) {
		this.complemento = complemento;
	}
	
	@Override
	public String toString() {
		return "Comprobante [emisor=" + emisor + ", receptor=" + receptor + ", complemento=" + complemento
				+ ", conceptos=" + conceptos + ", version=" + version + ", serie=" + serie + ", folio=" + folio
				+ ", fecha=" + fecha + ", sello=" + sello + ", formaPago=" + formaPago + ", noCertificado="
				+ noCertificado + ", certificado=" + certificado + ", condicionesDePago=" + condicionesDePago
				+ ", subTotal=" + subTotal + ", moneda=" + moneda + ", tipoCambio=" + tipoCambio + ", total=" + total
				+ ", tipoDeComprobante=" + tipoDeComprobante + ", metodoPago=" + metodoPago + ", lugarExpedicion="
				+ lugarExpedicion + "]";
	}
	

}
