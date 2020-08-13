package com.canfer.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{
	
}
