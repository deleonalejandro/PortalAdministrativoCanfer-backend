package com.canfer.app.cfd;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Conceptos")
public class Conceptos {
	
	private List<Concepto> conceptosList;
	
	public Conceptos() {
		// Constructor vacio
	}

	@XmlElement(name = "Concepto")
	public List<Concepto> getConceptosList() {
		return conceptosList;
	}

	public void setConceptosList(List<Concepto> conceptosList) {
		this.conceptosList = conceptosList;
	}

	@Override
	public String toString() {
		return "Conceptos [conceptosList=" + conceptosList + "]";
	}
	
	
	
	


}
