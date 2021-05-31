package com.canfer.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.DetFormularioCajaChica;
import com.canfer.app.model.Documento;
import com.canfer.app.model.FormularioCajaChica;

public interface DetFormularioCCRepository extends JpaRepository<DetFormularioCajaChica, Long>{
	
	List<DetFormularioCajaChica> findAllByFormularioCajaChica(FormularioCajaChica formularioCajaChica);
	
	Optional<DetFormularioCajaChica> findByDocumento(Documento documento);
	
}
