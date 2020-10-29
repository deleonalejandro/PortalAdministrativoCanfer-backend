package com.canfer.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Proveedor;

@NoRepositoryBean
public interface ComprobanteFiscalBaseRepository<T extends ComprobanteFiscal> extends JpaRepository<T, Long>{
	
	T findByUuid(String uuid);
	List<T> findAll();
	List<T> findAllByRfcEmpresaAndProveedor(String rfc, Proveedor proveedor);
}


