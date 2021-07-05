package com.canfer.app.dto;

import static org.hamcrest.CoreMatchers.nullValue;

import java.time.LocalDateTime;

public class FormularioCajaChicaDTO {
	
	private Long idFormularioCajaChica;
	private Float total;
	private String estatus;
	private String comentario;
	private String paqueteria;
	private String numeroGuia;
	private String numeroPago;
	private String fechaPago;
	private String fechaEnvio;
	
	public FormularioCajaChicaDTO() {
	}

	public Long getIdFormularioCajaChica() {
		return idFormularioCajaChica;
	}

	public void setIdFormularioCajaChica(Long idFormularioCajaChica) {
		this.idFormularioCajaChica = idFormularioCajaChica;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getPaqueteria() {
		return paqueteria;
	}

	public void setPaqueteria(String paqueteria) {
		this.paqueteria = paqueteria;
	}

	public String getNumeroGuia() {
		return numeroGuia;
	}

	public void setNumeroGuia(String numeroGuia) {
		this.numeroGuia = numeroGuia;
	}

	public String getNumeroPago() {
		return numeroPago;
	}

	public void setNumeroPago(String numeroPago) {
		this.numeroPago = numeroPago;
	}

	public String getFechaPago() {
		return fechaPago;
	}
	
	public LocalDateTime getFormattedFechaPago() {
		if (fechaPago.isEmpty()) {
			return null;
		}
		return LocalDateTime.parse(fechaPago);
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	
	public LocalDateTime getFormattedFechaEnvio() {
		if (fechaEnvio.isEmpty()) {
			return null;
		}
		return LocalDateTime.parse(fechaEnvio);
	}
	
	
	
	
	
	
	
	
	

}
