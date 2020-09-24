package com.canfer.app.repository;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal;
@Transactional
@Repository
public interface ComprobanteFiscalRepository extends ComprobanteFiscalBaseRepository<ComprobanteFiscal> {

}

