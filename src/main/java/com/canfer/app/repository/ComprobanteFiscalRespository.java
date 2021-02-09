package com.canfer.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.canfer.app.model.ComprobanteFiscal;
@Transactional
@Repository
public interface ComprobanteFiscalRespository extends ComprobanteFiscalBaseRepository<ComprobanteFiscal>, JpaSpecificationExecutor<ComprobanteFiscal> {
	

	@Transactional
	@Procedure("sp_PortalProveedores_ObtieneRS")
	void actualizaBitRs(String pServidor, String pBaseDatos, Long pId_Empresa);
}

