package com.canfer.app.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.Archivo;

@Repository
@Transactional
public interface ArchivoRepository extends ArchivoBaseRepository<Archivo> {
	
}
