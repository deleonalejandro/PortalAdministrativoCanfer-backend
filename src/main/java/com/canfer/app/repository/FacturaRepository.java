package com.canfer.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal.Factura;

@Transactional
@Repository
public interface FacturaRepository extends ComprobanteFiscalBaseRepository<Factura>, JpaSpecificationExecutor<Factura> {

	Factura findByRfcEmpresaAndRfcProveedorAndIdNumSap(String rfcEmpresa, String rfcProveedor, Long IdNumSap);
}

