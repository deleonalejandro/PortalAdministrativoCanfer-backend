package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{
	
	Proveedor findByRfc(String rfc);
	
	List<Proveedor> findAllByRfc(String rfc);

}

