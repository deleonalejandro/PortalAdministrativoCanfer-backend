package com.canfer.app.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Factura")
public class Factura extends ComprobanteFiscal {
	
	@JoinColumn(name = "uuidComplemento", nullable= true)
    @ManyToOne(targetEntity = FacturaNotaComplemento.class, fetch = FetchType.LAZY)
    private FacturaNotaComplemento complemento;
	
	public Factura() {
		super();
	}

	public FacturaNotaComplemento getComplemento() {
		return complemento;
	}

	public void setComplemento(FacturaNotaComplemento complemento) {
		this.complemento = complemento;
	}
	
	
}
