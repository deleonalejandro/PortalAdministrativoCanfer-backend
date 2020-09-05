package com.canfer.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal;

@NoRepositoryBean
@Repository
public interface ComprobanteFiscalRepository<T extends ComprobanteFiscal> extends JpaRepository<T, Long>{

}

