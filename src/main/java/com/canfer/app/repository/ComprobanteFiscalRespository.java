package com.canfer.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.IModuleEntity;
@Transactional
@Repository
public interface ComprobanteFiscalRespository extends ComprobanteFiscalBaseRepository<ComprobanteFiscal>, JpaSpecificationExecutor<ComprobanteFiscal> {
	
	List<IModuleEntity> findByAllEntityById(List<Long> ids);
	
}

