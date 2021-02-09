package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.FormularioCajaChica;

public interface FormularioCajaChicaRepository extends JpaRepository<FormularioCajaChica, Long>{

	List<ComprobanteFiscal> findAll(Specification<ComprobanteFiscal> comprobanteSpec);

}

