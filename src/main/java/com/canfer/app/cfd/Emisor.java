package com.canfer.app.cfd;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Emisor")
public class Emisor {
	
	private String rfc;
	private String nombre;
	private String regimenFiscal;
	
	public Emisor() {
		//Constructor vacio
	}

	public String getRfc() {
		return rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	@XmlAttribute(name = "Rfc")
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@XmlAttribute(name = "Nombre")
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute(name = "RegimenFiscal")
	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	@Override
	public String toString() {
		return "Emisor [rfc=" + rfc + ", nombre=" + nombre + ", regimenFiscal=" + regimenFiscal + "]";
	}
	
	
	
	

}
