package com.canfer.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.Documento;

@Transactional
@Repository
public interface DocumentoRepository extends DocumentoBaseRepository<Documento> {

	List<Documento> findAllByIdTablaAndExtension(Long id, String extension);
	
	Documento findByConceptoAndExtension(String concepto, String extension);
	
	Documento findByConceptoAndIdTabla(String concepto, Long idTabla);

	List<Documento> findAllByConcepto(String concepto);

	List<Documento> findAllByConceptoAndExtension(String concepto, String extension);
	

}
