package com.canfer.app.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

	
@Entity(name = "FacturaNotaComplemento")
public class FacturaNotaComplemento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFnc;
	
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
	
	@JoinColumn(name = "uuidComplemento", nullable= true)
    @ManyToOne(targetEntity = FacturaNotaComplemento.class, fetch = FetchType.LAZY)
    private FacturaNotaComplemento complemento;
	

	//Constructor 
	public FacturaNotaComplemento() {
		super();
		this.estatusPago = "EN PROCESO";
		this.bitRS = false;
		this.bitRSusuario = false;
		this.comentario = "";
	}


	//Getters and Setters
	
	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
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



	public String getNoCertificadoSat() {
		return noCertificadoSat;
	}



	public void setNoCertificadoSat(String noCertificadoSat) {
		this.noCertificadoSat = noCertificadoSat;
	}



	public String getVersionCfd() {
		return versionCfd;
	}



	public void setVersionCfd(String versionCfd) {
		this.versionCfd = versionCfd;
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

	

	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
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



	public String getEstatusPago() {
		return estatusPago;
	}



	public void setEstatusPago(String estatusPago) {
		this.estatusPago = estatusPago;
	}



	public String getRespuestaValidacion() {
		return respuestaValidacion;
	}



	public void setRespuestaValidacion(String respuestaValidacion) {
		this.respuestaValidacion = respuestaValidacion;
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
	

	public String getUuidRelacionados() {
		return uuidRelacionados;
	}


	public void setUuidRelacionados(String uuidRelacionados) {
		this.uuidRelacionados = uuidRelacionados;
	}
	
	
	public void addUuidRelacionados(String uuid) {
		if (this.uuidRelacionados.isEmpty()) {
			this.uuidRelacionados = uuid;
		} else {
			this.uuidRelacionados = this.uuidRelacionados + "," + uuid;
		}
	}
	
	@JsonIgnore
	public List<String> getUuidRelacionadosList(){
		if (this.uuidRelacionados.isEmpty()) {
			return Collections.emptyList();
		}
		return Arrays.asList(this.uuidRelacionados.split(",")); 
	}


	public FacturaNotaComplemento getComplemento() {
		return complemento;
	}



	public void setComplemento(FacturaNotaComplemento complemento) {
		this.complemento = complemento;
	}



	public String getComentario() {
		return comentario;
	}



	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	public Long getIdFnc() {
		return idFnc;
	}


	public void setIdFnc(Long idFnc) {
		this.idFnc = idFnc;
	}


	public String getTipoRelacionUuidRelacionados() {
		return tipoRelacionUuidRelacionados;
	}


	public void setTipoRelacionUuidRelacionados(String tipoRelacionUuidRelacionados) {
		this.tipoRelacionUuidRelacionados = tipoRelacionUuidRelacionados;
	}


	public String getEstatusSAT() {
		return estatusSAT;
	}


	public void setEstatusSAT(String estatusSAT) {
		this.estatusSAT = estatusSAT;
	}
	
	
	



	
	
	
	


	
	
}