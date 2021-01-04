
package com.canfer.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Pago;

@Transactional
@Repository
public interface FacturaRepository extends ComprobanteFiscalBaseRepository<Factura>, JpaSpecificationExecutor<Factura> {

	Factura findByRfcEmpresaAndRfcProveedorAndIdNumSap(String rfcEmpresa, String rfcProveedor, Long IdNumSap);
	List<Factura> findAllByComplemento(ComplementoPago complemento);
	ComprobanteFiscal findByPago(Pago pago);
  
}

