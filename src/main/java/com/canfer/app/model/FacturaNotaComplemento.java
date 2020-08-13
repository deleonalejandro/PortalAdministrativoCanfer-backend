package com.canfer.app.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne; 

	
@Entity(name = "FacturaNotaComplemento")
public class FacturaNotaComplemento {

	@Id
	@Column(nullable = false)
	private String uuid; 
	
	@JoinColumn(name = "XML")
	@OneToOne(targetEntity = Documento.class, fetch= FetchType.EAGER)
	private Documento xmlDocumento;
	
	@JoinColumn(name = "PDF", nullable = true)
	@OneToOne(targetEntity = Documento.class, fetch= FetchType.EAGER)
	private Documento pdfDocumento;
	
	@JoinColumn(name = "Documentos Relacionados")
	@ManyToMany(targetEntity = FacturaNotaComplemento.class, fetch = FetchType.EAGER)
	private List<FacturaNotaComplemento>  documentosRelacionados;
	
	@Column(nullable = false)
	private Integer idNumSap;
	
	@JoinColumn(name = "idEmpresa")
    @ManyToOne(targetEntity = Empresa.class, fetch = FetchType.EAGER)
    private Empresa empresa;
	
	@JoinColumn(name = "idProveedor")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.EAGER)
    private Proveedor proveedor;
	 	
	@Column(nullable = false)
	private String serie; 

	@Column(nullable = false)
	private String folio; 
	
	@Column(nullable = false)
	private String rfcEmpresa; 
	
	@Column(nullable = false)
	private String rfcProveedor; 
	
	@Column(nullable = false)
	private String fechaEmision; 
	
	@Column(nullable = false)
	private String fechaTimbre; 
	
	@Column(nullable = false)
	private String noCertificadoEmpresa; 
	
	@Column(nullable = false)
	private String noCertificadoSAT; 
	
	@Column(nullable = false)
	private String versionCFD; 
	
	@Column(nullable = false)
	private String versionTimbre; 
	
	@Column(nullable = false)
	private String moneda; 
	
	@Column(nullable = false)
	private Float total; 
	
	@Column(nullable = true)
	private String pdfDate; 
	
	@Column(nullable = false)
	private String xmlDate; 

	@Column(nullable = false)
	private String tipoDocumento; 
	
	@Column(nullable = false)
	private Boolean bitValidoSAT; 
	
	@Column(nullable = false)
	private String estatus; 
	
	@Column(nullable = false)
	private String respuestaValidacion; 
	
	@Column(nullable = true)
	private String errorValidacion; 
	 
	@Column(nullable = false)
	private Boolean bitRS; 
	
	@Column(nullable = false)
	private Boolean bitRSusuario;
	
	@JoinColumn(name = "uuidComplemento", nullable= true)
    @ManyToOne(targetEntity = FacturaNotaComplemento.class, fetch = FetchType.LAZY)
    private FacturaNotaComplemento facturaNotaComplemento;

	//Constructor 
	
	public FacturaNotaComplemento() {
		super();
		
	}
	
	
	
	public FacturaNotaComplemento(String pdfDate, String xmlDate, Boolean bitValidoSAT, String estatus, Boolean bitRS,
			Boolean bitRSusuario) {
		
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date); 
        
		this.xmlDate = strDate;
		this.bitValidoSAT = false;
		this.estatus = "En Proceso";
		this.bitRS = false;
		this.bitRSusuario = false;
	}


	//Getters and Setters
	
	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public Documento getXmlDocumento() {
		return xmlDocumento;
	}



	public void setXmlDocumento(Documento xmlDocumento) {
		this.xmlDocumento = xmlDocumento;
	}



	public Documento getPdfDocumento() {
		return pdfDocumento;
	}



	public void setPdfDocumento(Documento pdfDocumento) {
		this.pdfDocumento = pdfDocumento;
	}



	public List<FacturaNotaComplemento> getDocumentosRelacionados() {
		return documentosRelacionados;
	}



	public void setDocumentosRelacionados(List<FacturaNotaComplemento> documentosRelacionados) {
		this.documentosRelacionados = documentosRelacionados;
	}



	public Integer getIdNumSap() {
		return idNumSap;
	}



	public void setIdNumSap(Integer idNumSap) {
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



	public FacturaNotaComplemento getFacturaNotaComplemento() {
		return facturaNotaComplemento;
	}



	public void setFacturaNotaComplemento(FacturaNotaComplemento facturaNotaComplemento) {
		this.facturaNotaComplemento = facturaNotaComplemento;
	}

	
	
}