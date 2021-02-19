package com.canfer.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.FormularioCajaChica;
import com.canfer.app.model.Sucursal;

public interface FormularioCajaChicaRepository extends JpaRepository<FormularioCajaChica, Long>{

	List<FormularioCajaChica> findAllBySucursal(Sucursal sucursal);

}

