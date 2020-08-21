package com.canfer.app.cfd;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Complemento")
public class Complemento {
	
	private TimbreFiscalDigital timbreFiscalDigital;
	
	public Complemento() {
		// Constructor vacio
	}

	@XmlElement(namespace = "http://www.sat.gob.mx/TimbreFiscalDigital" , name = "TimbreFiscalDigital")
	public TimbreFiscalDigital getTimbreFiscalDigital() {
		return timbreFiscalDigital;
	}

	public void setTimbreFiscalDigital(TimbreFiscalDigital timbreFiscalDigital) {
		this.timbreFiscalDigital = timbreFiscalDigital;
	}

	@Override
	public String toString() {
		return "Complemento [timbreFiscalDigital=" + timbreFiscalDigital + "]";
	}
	
	

}
