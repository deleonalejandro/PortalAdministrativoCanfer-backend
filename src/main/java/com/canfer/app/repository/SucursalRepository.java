package com.canfer.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
	
	Optional<Sucursal> findByEmpresaAndClaveProv(Empresa empresa, String claveProv);
	

}

