package com.canfer.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;

@Transactional
@Repository
public interface FacturaRepository extends ComprobanteFiscalBaseRepository<Factura>, JpaSpecificationExecutor<Factura> {

	List<Factura> findAllByComplemento(ComplementoPago complemento);
	
}

