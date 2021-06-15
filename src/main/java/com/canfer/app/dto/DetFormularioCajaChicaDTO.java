package com.canfer.app.dto;

import java.time.LocalDateTime;

public class DetFormularioCajaChicaDTO {
	
	private Long idDetFormularioCC;
	private Long idSucursal;
	private Long idFormulario;
	private Long idClasificacion;
	private Float monto; 
	private String realDate;
	private String beneficiario;
	private String folio;
	private String nombreProveedor;
	private Boolean bitRS;
	private Float total;
	private Float subTotal;
	
	public DetFormularioCajaChicaDTO() {
	}
	
	public Long getIdDetFormularioCC() {
		return idDetFormularioCC;
	}

	public void setIdDetFormularioCC(Long idDetFormularioCC) {
		this.idDetFormularioCC = idDetFormularioCC;
	}

	public Long getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(Long idFormulario) {
		this.idFormulario = idFormulario;
	}

	public Long getIdClasificacion() {
		return idClasificacion;
	}

	public void setIdClasificacion(Long idClasificacion) {
		this.idClasificacion = idClasificacion;
	}

	public Float getMonto() {
		return monto;
	}

	public void setMonto(Float monto) {
		this.monto = monto;
	}

	public String getRealDate() {
		return realDate;
	}

	public void setRealDate(String realDate) {
		this.realDate = realDate;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String responsable) {
		this.beneficiario = responsable;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public Long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(Long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	
	public LocalDateTime getFormattedDate() {
		return LocalDateTime.parse(realDate);
	}

	public Boolean getBitRS() {
		return bitRS;
	}

	public void setBitRS(Boolean bitRS) {
		this.bitRS = bitRS;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}
	
	
	 

}
