package com.canfer.app.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.Documento;
@Transactional
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	

}
