package com.canfer.app.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NotaDeCredito")
public class NotaDeCredito extends ComprobanteFiscal {

	public NotaDeCredito() {
		super();
	}
	
}
