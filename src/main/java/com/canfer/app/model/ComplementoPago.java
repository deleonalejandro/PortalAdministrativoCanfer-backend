package com.canfer.app.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ComplementoDePago")
public class ComplementoPago extends ComprobanteFiscal {
	
	public ComplementoPago() {
		super();
	}

}
