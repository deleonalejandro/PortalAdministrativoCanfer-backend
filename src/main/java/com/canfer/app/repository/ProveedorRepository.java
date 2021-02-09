package com.canfer.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
	
	Proveedor findByRfc(String rfc);
	
	List<Proveedor> findAllByRfc(String rfc);

	List<Proveedor> findAllByRfcAndBitActivo(String rfc, boolean activo);
	
	Proveedor findByEmpresasAndRfc(Empresa empresa, String rfc);
	
	Optional<Proveedor> findByEmpresasAndClaveProv(Empresa empresa, String claveProv);
	
	List<Proveedor> findAllByEmpresasAndRfc(Empresa empresa, String rfc);
	
	Proveedor findByEmpresasAndNombre(Empresa empresa, String nombre);
	
	List<Proveedor> findAllByEmpresas(Empresa empresa);
	
	@Procedure("sp_PortalProveedores_ActualizaProveedores")
	void updateSuppliersFromSap(String pServidor, String pBaseDatos, Long pId_Empresa);
	


}

