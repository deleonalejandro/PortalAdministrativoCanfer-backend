package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long>{

	List<Pago> findByBitProcesadoAndNuevoEstatusFactura(Boolean bitProcesado, String nuevoEstatusFacturP);
	List<Pago> findByBitProcesado(Boolean bitProcesado);
	List<Pago> findByBitProcesadoAndRfcEmpresa(boolean b, String rfcEmpresa);
	Pago findByRfcEmpresaAndRfcProveedorAndIdNumSap(String rfcEmpresa, String rfcProveedor, Long idNumSap);
	List<Pago> findAllByBitProcesadoAndRfcEmpresaAndClaveProveedor(boolean b, String rfc, String clave);
}
