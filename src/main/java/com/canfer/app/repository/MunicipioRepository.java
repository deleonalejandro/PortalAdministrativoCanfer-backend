package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Estado;
import com.canfer.app.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Long>{
	
	List<Municipio> findAllByEstado(Estado estado); 

}

