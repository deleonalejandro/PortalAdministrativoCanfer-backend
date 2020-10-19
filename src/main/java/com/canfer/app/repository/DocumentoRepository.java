package com.canfer.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.Documento;

@Transactional
@Repository
public interface DocumentoRepository extends DocumentoBaseRepository<Documento> {

	List<Documento> findAllByIdTablaAndExtension(Long id, String extension);

}
