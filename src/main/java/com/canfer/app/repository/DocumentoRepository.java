package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long>{
	
	List<Documento> findAllByIdTabla(Long idTabla);
	
}
