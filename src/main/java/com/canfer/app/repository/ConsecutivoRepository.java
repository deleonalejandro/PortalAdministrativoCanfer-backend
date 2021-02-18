package com.canfer.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Consecutivo;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Sucursal;

public interface ConsecutivoRepository extends JpaRepository<Consecutivo, Long>{
	
	Consecutivo findByEmpresaAndModulo(Empresa empresa, String modulo);
	
	Consecutivo findBySucursal(Sucursal sucursal);
	
}
