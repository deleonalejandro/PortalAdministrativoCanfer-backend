package com.canfer.app.cfd;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Receptor")
public class Receptor {
	
	private String rfc;
	private String nombre;
	private String usoCFDI;
	
	public Receptor() {
		//Constructor vacio
	}

	public String getRfc() {
		return rfc;
	}

	public String getNombre() {
		return nombre;
	}

	public String getUsoCFDI() {
		return usoCFDI;
	}

	@XmlAttribute(name = "Rfc")
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@XmlAttribute(name = "Nombre")
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute(name = "UsoCFDI")
	public void setUsoCDFI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	@Override
	public String toString() {
		return "Receptor [rfc=" + rfc + ", nombre=" + nombre + ", usoCFDI=" + usoCFDI + "]";
	}
	
	
	
	

}
