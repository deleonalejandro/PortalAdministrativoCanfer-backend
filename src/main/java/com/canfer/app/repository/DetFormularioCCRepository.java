package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.DetFormularioCajaChica;
import com.canfer.app.model.FormularioCajaChica;

public interface DetFormularioCCRepository extends JpaRepository<DetFormularioCajaChica, Long>{
	
	List<DetFormularioCajaChica> findAllByFormularioCajaChica(FormularioCajaChica formularioCajaChica);
	
}
