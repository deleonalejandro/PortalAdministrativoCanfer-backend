package com.canfer.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal;
@Transactional
@Repository
public interface ComprobanteFiscalRespository extends ComprobanteFiscalBaseRepository<ComprobanteFiscal>, JpaSpecificationExecutor<ComprobanteFiscal> {

}

