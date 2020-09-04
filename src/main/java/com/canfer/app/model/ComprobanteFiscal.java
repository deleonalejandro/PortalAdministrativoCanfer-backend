package com.canfer.app.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Comprobante")
public abstract class ComprobanteFiscal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idComprobanteFiscal;
	
	@Column(nullable = false)
	private String uuid; 
	
	@Column(nullable = false)
	private Long idNumSap;
	
	@JoinColumn(name = "idEmpresa")
    @ManyToOne(targetEntity = Empresa.class, fetch = FetchType.EAGER)
    private Empresa empresa;
	
	@JoinColumn(name = "idProveedor")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.EAGER)
    private Proveedor proveedor;
	 	
	@Column
	private String serie; 

	@Column
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
	private String noCertificadoSat; 
	
	@Column(nullable = false)
	private String versionCfd; 
	
	@Column(nullable = false)
	private String versionTimbre; 
	
	@Column(nullable = false)
	private String moneda; 
	
	@Column(nullable = false)
	private String total; 
	
	@Column(nullable = false)
	private String tipoDocumento; 
	
	@Column
	private Boolean bitValidoSAT; 
	
	@Column
	private String respuestaValidacion; 
	
	@Column
	private String estatusSAT;
	
	@Column(nullable = false)
	private String estatusPago; 
	 
	@Column(nullable = false)
	private Boolean bitRS; 
	
	@Column(nullable = false)
	private Boolean bitRSusuario;
	
	@Column
	private String uuidRelacionados;
	
	@Column
	private String tipoRelacionUuidRelacionados;
	
	@Column
	private String comentario;

	//Constructor 
	public ComprobanteFiscal() {
		this.estatusPago = "EN PROCESO";
		this.bitRS = false;
		this.bitRSusuario = false;
		this.comentario = "";
	}

	protected Long getIdComprobanteFiscal() {
		return idComprobanteFiscal;
	}

	protected void setIdComprobanteFiscal(Long idComprobanteFiscal) {
		this.idComprobanteFiscal = idComprobanteFiscal;
	}

	protected String getUuid() {
		return uuid;
	}

	protected void setUuid(String uuid) {
		this.uuid = uuid;
	}

	protected Long getIdNumSap() {
		return idNumSap;
	}

	protected void setIdNumSap(Long idNumSap) {
		this.idNumSap = idNumSap;
	}

	protected Empresa getEmpresa() {
		return empresa;
	}

	protected void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	protected Proveedor getProveedor() {
		return proveedor;
	}

	protected void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	protected String getSerie() {
		return serie;
	}

	protected void setSerie(String serie) {
		this.serie = serie;
	}

	protected String getFolio() {
		return folio;
	}

	protected void setFolio(String folio) {
		this.folio = folio;
	}

	protected String getRfcEmpresa() {
		return rfcEmpresa;
	}

	protected void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}

	protected String getRfcProveedor() {
		return rfcProveedor;
	}

	protected void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}

	protected String getFechaEmision() {
		return fechaEmision;
	}

	protected void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	protected String getFechaTimbre() {
		return fechaTimbre;
	}

	protected void setFechaTimbre(String fechaTimbre) {
		this.fechaTimbre = fechaTimbre;
	}

	protected String getNoCertificadoEmpresa() {
		return noCertificadoEmpresa;
	}

	protected void setNoCertificadoEmpresa(String noCertificadoEmpresa) {
		this.noCertificadoEmpresa = noCertificadoEmpresa;
	}

	protected String getNoCertificadoSat() {
		return noCertificadoSat;
	}

	protected void setNoCertificadoSat(String noCertificadoSat) {
		this.noCertificadoSat = noCertificadoSat;
	}

	protected String getVersionCfd() {
		return versionCfd;
	}

	protected void setVersionCfd(String versionCfd) {
		this.versionCfd = versionCfd;
	}

	protected String getVersionTimbre() {
		return versionTimbre;
	}

	protected void setVersionTimbre(String versionTimbre) {
		this.versionTimbre = versionTimbre;
	}

	protected String getMoneda() {
		return moneda;
	}

	protected void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	protected String getTotal() {
		return total;
	}

	protected void setTotal(String total) {
		this.total = total;
	}

	protected String getTipoDocumento() {
		return tipoDocumento;
	}

	protected void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	protected Boolean getBitValidoSAT() {
		return bitValidoSAT;
	}

	protected void setBitValidoSAT(Boolean bitValidoSAT) {
		this.bitValidoSAT = bitValidoSAT;
	}

	protected String getRespuestaValidacion() {
		return respuestaValidacion;
	}

	protected void setRespuestaValidacion(String respuestaValidacion) {
		this.respuestaValidacion = respuestaValidacion;
	}

	protected String getEstatusSAT() {
		return estatusSAT;
	}

	protected void setEstatusSAT(String estatusSAT) {
		this.estatusSAT = estatusSAT;
	}

	protected String getEstatusPago() {
		return estatusPago;
	}

	protected void setEstatusPago(String estatusPago) {
		this.estatusPago = estatusPago;
	}

	protected Boolean getBitRS() {
		return bitRS;
	}

	protected void setBitRS(Boolean bitRS) {
		this.bitRS = bitRS;
	}

	protected Boolean getBitRSusuario() {
		return bitRSusuario;
	}

	protected void setBitRSusuario(Boolean bitRSusuario) {
		this.bitRSusuario = bitRSusuario;
	}

	protected String getComentario() {
		return comentario;
	}

	protected void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	protected String getUuidRelacionados() {
		return uuidRelacionados;
	}

	protected void setUuidRelacionados(String uuidRelacionados) {
		this.uuidRelacionados = uuidRelacionados;
	}

	protected String getTipoRelacionUuidRelacionados() {
		return tipoRelacionUuidRelacionados;
	}

	protected void setTipoRelacionUuidRelacionados(String tipoRelacionUuidRelacionados) {
		this.tipoRelacionUuidRelacionados = tipoRelacionUuidRelacionados;
	}
	
	protected void addUuidRelacionados(String uuid) {
		if (this.uuidRelacionados.isEmpty()) {
			this.uuidRelacionados = uuid;
		} else {
			this.uuidRelacionados = this.uuidRelacionados + "," + uuid;
		}
	}
	
	@JsonIgnore
	protected List<String> getUuidRelacionadosList(){
		if (this.uuidRelacionados.isEmpty()) {
			return Collections.emptyList();
		}
		return Arrays.asList(this.uuidRelacionados.split(",")); 
	}

	
	
	
	

}
