package com.canfer.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Sucursal;
import com.canfer.app.model.Usuario.UsuarioCanfer;

public interface SucursalRepository extends JpaRepository<Sucursal, Long>{
	
	Optional<Sucursal> findByEmpresaAndClaveProv(Empresa empresa, String claveProv);
	
	List<Sucursal> findAllByUsuariosCanfer(UsuarioCanfer usuarioCanfer);
	
	List<Sucursal> findAllByEmpresa(Empresa empresa);
	
	List<Sucursal> findAllByEmpresaIn(List<Empresa> empresas);
	
	List<Sucursal> findAllByOrderByEmpresa();
	
	
	
	

}

