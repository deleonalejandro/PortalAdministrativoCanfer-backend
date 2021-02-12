package com.canfer.app.repository;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.Documento;
@Transactional
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	Optional<Documento> findByArchivoPDF(ArchivoPDF archivoPDF);
	
	Optional<Documento> findByArchivoXML(ArchivoXML archivoXML);
	

}
