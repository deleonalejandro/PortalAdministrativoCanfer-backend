package com.canfer.app.cfd;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CfdiRelacionados")
public class CfdiRelacionados {
	
	private String tipoRelacion;
	private List<CfdiRelacionado> cdfiList;
	
	public CfdiRelacionados() {
		// Constructor vacio
	}

	@XmlAttribute(name = "TipoRelacion")
	public String getTipoRelacion() {
		return tipoRelacion;
	}

	@XmlElement(name = "CfdiRelacionado")
	public List<CfdiRelacionado> getCdfiList() {
		return cdfiList;
	}

	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}

	public void setCdfiList(List<CfdiRelacionado> cdfiList) {
		this.cdfiList = cdfiList;
	}
	
	

}
