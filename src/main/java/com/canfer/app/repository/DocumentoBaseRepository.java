package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.canfer.app.model.Documento;

@NoRepositoryBean
public interface DocumentoBaseRepository<T extends Documento> extends JpaRepository<T, Long>{
	
	T findByIdTablaAndExtension(Long idTabla, String extension);
	
	List<T> findAllByIdTabla(Long idTabla);
	

	
}
