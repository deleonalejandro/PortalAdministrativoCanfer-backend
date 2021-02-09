package com.canfer.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.canfer.app.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
	
	// this function was created to test SP
	@Procedure("insertEstado")
	void agregaEstado(String pNombre);

}

