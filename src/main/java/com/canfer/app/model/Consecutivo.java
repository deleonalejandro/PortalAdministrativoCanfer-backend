package com.canfer.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "Consecutivo")
public class Consecutivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idConsecutivo;
	
	@JoinColumn(name = "idEmpresa")
    @ManyToOne(targetEntity = Empresa.class, fetch = FetchType.LAZY)
    private Empresa empresa;
	
	@JoinColumn(name = "id_sucursal")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sucursal sucursal;
	
	@Column(nullable = false)
	private String modulo;
	
	@Column(nullable = false)
	private Long initialNum;
	
	@Column(nullable = false)
	private Long finalNum;
	
	@Column(nullable = false)
	private Long currentNum;
	

	public Consecutivo() {
		// Constructor vacio
	}
	public Consecutivo(Empresa empresa, Sucursal sucursal, String modulo, Long initialNum, Long finalNum,
			Long currentNum) {
		super();
		this.empresa = empresa;
		this.sucursal = sucursal;
		this.modulo = modulo;
		this.initialNum = initialNum;
		this.finalNum = finalNum;
		this.currentNum = currentNum;
	}

	public long getIdConsecutivo() {
		return idConsecutivo;
	}

	public void setIdConsecutivo(long idConsecutivo) {
		this.idConsecutivo = idConsecutivo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public Long getInitialNum() {
		return initialNum;
	}

	public void setInitialNum(Long initialNum) {
		this.initialNum = initialNum;
	}

	public Long getFinalNum() {
		return finalNum;
	}

	public void setFinalNum(Long finalNum) {
		this.finalNum = finalNum;
	}

	public Long getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Long currentNum) {
		this.currentNum = currentNum;
	}
	
	// always start sequence in zero.
	public Long getNext() {
		this.currentNum = this.currentNum + 1;
		return this.currentNum;
	}
	
	
	
	

}
