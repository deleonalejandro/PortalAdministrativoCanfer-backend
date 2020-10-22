package com.canfer.app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Usuario.UsuarioCanfer;

@Repository
public interface UsuarioCanferRepository extends UsuarioBaseRepository<UsuarioCanfer>{
	

	List<UsuarioCanfer> findAllByEmpresas(Empresa findByRfc);
}
