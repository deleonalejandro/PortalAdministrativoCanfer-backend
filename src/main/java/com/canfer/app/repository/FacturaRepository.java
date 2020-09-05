package com.canfer.app.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal.Factura;

@Transactional
@Repository
public interface FacturaRepository extends ComprobanteFiscalRepository<Factura> {

}

