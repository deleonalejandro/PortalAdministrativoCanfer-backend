package com.canfer.app.cfd;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CfdiRelacionado")
public class CfdiRelacionado {
	
	private String uuid;
	
	public CfdiRelacionado() {
		// Constructor vacio
	}

	public String getUuid() {
		return uuid;
	}

	@XmlAttribute(name = "UUID")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

}
