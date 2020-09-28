package com.canfer.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
	
	List<Pago> findByBitProcesado(Boolean bitProcesado);

}

