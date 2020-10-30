package com.canfer.app.dto;




import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Proveedor;

public class ComprobanteFiscalDTO {
	//Transfer fields declaration.
	private Long idComprobanteFiscal;
	private String uuid; 
	private String idDocumento;
	private Long idNumSap;
	private Empresa empresa;
	private Proveedor proveedor;
	private Long idProveedor;
	private String serie;
	private String folio;
	private String rfcEmpresa;
	private String rfcProveedor;
	private String fechaEmision;
	private String fechaTimbre;
	private String noCertificadoEmpresa;
	private String noCertificadoSAT;
	private String versionCFD;
	private String versionTimbre;
	private String moneda;
	private Float total;
	private String pdfDate;
	private String xmlDate;
	private String tipoDocumento;
	private Boolean bitValidoSAT;
	private String estatus;
	private String respuestaValidacion;
	private String errorValidacion;
	private Boolean bitRS;
	private Boolean bitRSusuario;
	private String comentario;
	
	//Constructor
	public ComprobanteFiscalDTO() {
		// Empty constructor, no parameters. 
	}


	public Long getIdProveedor() {
		return idProveedor;
	}



	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Long getIdComprobanteFiscal() {
		return idComprobanteFiscal;
	}
	
	public void setIdComprobanteFiscal(Long idComprobanteFiscal) {
		this.idComprobanteFiscal = idComprobanteFiscal;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Long getIdNumSap() {
		return idNumSap;
	}

	public void setIdNumSap(Long idNumSap) {
		this.idNumSap = idNumSap;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}


	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	
	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getRfcEmpresa() {
		return rfcEmpresa;
	}

	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}

	public String getRfcProveedor() {
		return rfcProveedor;
	}

	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getFechaTimbre() {
		return fechaTimbre;
	}

	public void setFechaTimbre(String fechaTimbre) {
		this.fechaTimbre = fechaTimbre;
	}

	public String getNoCertificadoEmpresa() {
		return noCertificadoEmpresa;
	}

	public void setNoCertificadoEmpresa(String noCertificadoEmpresa) {
		this.noCertificadoEmpresa = noCertificadoEmpresa;
	}

	public String getNoCertificadoSAT() {
		return noCertificadoSAT;
	}

	public void setNoCertificadoSAT(String noCertificadoSAT) {
		this.noCertificadoSAT = noCertificadoSAT;
	}

	public String getVersionCFD() {
		return versionCFD;
	}

	public void setVersionCFD(String versionCFD) {
		this.versionCFD = versionCFD;
	}

	public String getVersionTimbre() {
		return versionTimbre;
	}

	public void setVersionTimbre(String versionTimbre) {
		this.versionTimbre = versionTimbre;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getPdfDate() {
		return pdfDate;
	}

	public void setPdfDate(String pdfDate) {
		this.pdfDate = pdfDate;
	}

	public String getXmlDate() {
		return xmlDate;
	}

	public void setXmlDate(String xmlDate) {
		this.xmlDate = xmlDate;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getBitValidoSAT() {
		return bitValidoSAT;
	}

	public void setBitValidoSAT(Boolean bitValidoSAT) {
		this.bitValidoSAT = bitValidoSAT;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getRespuestaValidacion() {
		return respuestaValidacion;
	}

	public void setRespuestaValidacion(String respuestaValidacion) {
		this.respuestaValidacion = respuestaValidacion;
	}

	public String getErrorValidacion() {
		return errorValidacion;
	}

	public void setErrorValidacion(String errorValidacion) {
		this.errorValidacion = errorValidacion;
	}

	public Boolean getBitRS() {
		return bitRS;
	}

	public void setBitRS(Boolean bitRS) {
		this.bitRS = bitRS;
	}

	public Boolean getBitRSusuario() {
		return bitRSusuario;
	}

	public void setBitRSusuario(Boolean bitRSusuario) {
		this.bitRSusuario = bitRSusuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	
}
