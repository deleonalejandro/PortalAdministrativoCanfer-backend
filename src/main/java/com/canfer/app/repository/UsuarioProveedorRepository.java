package com.canfer.app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario.UsuarioProveedor;

@Repository
public interface UsuarioProveedorRepository extends UsuarioBaseRepository<UsuarioProveedor>{
	
	List<UsuarioProveedor> findAllByProveedores(Proveedor proveedor);
	
	
	
}
