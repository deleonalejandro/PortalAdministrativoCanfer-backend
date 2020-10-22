package com.canfer.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{
	
	Proveedor findByRfc(String rfc);
	
	List<Proveedor> findAllByRfc(String rfc);

	Proveedor findByEmpresasAndRfc(Empresa empresa, String rfc);
	
	Optional<Proveedor> findByEmpresasAndClaveProv(Empresa empresa, String claveProv);
	
	List<Proveedor> findAllByEmpresasAndRfc(Empresa empresa, String rfc);
	
	Proveedor findByEmpresasAndNombre(Empresa empresa, String nombre);

}

