package com.canfer.app.cfd;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TimbreFiscalDigital")
public class TimbreFiscalDigital {
	
	private String version;
	private String uuid;
	private String fechaTimbrado;
	private String rfcProvCertif;
	private String selloCFD;
	private String noCertificadoSat;
	private String selloSAT;
	
	public TimbreFiscalDigital() {
		// Constructor vacio
	}

	public String getVersion() {
		return version;
	}

	public String getUuid() {
		return uuid;
	}

	public String getFechaTimbrado() {
		return fechaTimbrado;
	}

	public String getRfcProvCertif() {
		return rfcProvCertif;
	}

	public String getSelloCFD() {
		return selloCFD;
	}

	public String getNoCertificadoSat() {
		return noCertificadoSat;
	}

	public String getSelloSAT() {
		return selloSAT;
	}

	@XmlAttribute(name = "Version")
	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "UUID")
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@XmlAttribute(name = "FechaTimbrado")
	public void setFechaTimbrado(String fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	@XmlAttribute(name = "RfcProvCertif")
	public void setRfcProvCertif(String rfcProvCertif) {
		this.rfcProvCertif = rfcProvCertif;
	}

	@XmlAttribute(name = "SelloCFD")
	public void setSelloCFD(String selloCFD) {
		this.selloCFD = selloCFD;
	}

	@XmlAttribute(name = "NoCertificadoSAT")
	public void setNoCertificadoSat(String noCertificadoSat) {
		this.noCertificadoSat = noCertificadoSat;
	}
	
	@XmlAttribute(name = "SelloSAT")
	public void setSelloSAT(String selloSAT) {
		this.selloSAT = selloSAT;
	}

	@Override
	public String toString() {
		return "TimbreFiscalDigital [version=" + version + ", uuid=" + uuid + ", fechaTimbrado=" + fechaTimbrado
				+ ", rfcProvCertif=" + rfcProvCertif + ", selloCFD=" + selloCFD + ", noCertificadoSat="
				+ noCertificadoSat + ", selloSAT=" + selloSAT + "]";
	}
	
	
	
	

}
