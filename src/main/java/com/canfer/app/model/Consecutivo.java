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
	
	@JoinColumn(name = "claveSucursal")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    private Proveedor sucursal;
	
	@Column(nullable = false)
	private String modulo;
	
	@Column(nullable = false)
	private Long initialNum;
	
	@Column(nullable = false)
	private Long finalNum;
	
	@Column(nullable = false)
	private Long currentNum;

}
