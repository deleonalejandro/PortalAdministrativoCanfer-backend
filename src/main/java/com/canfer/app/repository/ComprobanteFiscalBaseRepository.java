package com.canfer.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Empresa;

@NoRepositoryBean
public interface ComprobanteFiscalBaseRepository<T extends ComprobanteFiscal> extends JpaRepository<T, Long>{
	
	T findByUuid(String uuid);
	T findByIdNumSapAndEmpresa(String idNumSap, Empresa empresa);
}


