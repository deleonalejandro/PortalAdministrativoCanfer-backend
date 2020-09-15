package com.canfer.app.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;

@Transactional
@Repository
public interface ComplementoPagoRepository extends ComprobanteFiscalBaseRepository<ComplementoPago> {

}

