package com.canfer.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.JsonReader;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario;
import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.canfer.app.model.Usuario.UsuarioProveedor;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.UsuarioCanferRepository;
import com.canfer.app.repository.UsuarioProveedorRepository;
import com.canfer.app.repository.UsuarioRepository;

@RestController
@RequestMapping("/catalogsAPI")
public class CatalogoRestController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioCanferRepository usuarioCanferRepo;
	
	@Autowired
	private UsuarioProveedorRepository usuarioProvRepo;
	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private JsonReader jsonReader;
	
	public CatalogoRestController() {
		// Este es el constructor vacio del controlador
	}
	
	@GetMapping(value = "/getUsersPA")
	public List<UsuarioCanfer> getAllUsersCanfer(){
		return usuarioCanferRepo.findAll();
	}
	
	@GetMapping(value = "/getUsersPP")
	public List<UsuarioProveedor> getAllUsersProveedor(){
		return usuarioProvRepo.findAll();
	}
	
	@GetMapping(value = "/getSuppliers")
	public List<Proveedor> getAllSuppliers(){
		return proveedorRepository.findAll();
	}
	
	@GetMapping(value = "/getCompanies")
	public List<Empresa> getAllCompanies() {
		return empresaRepository.findAll();
	}
	
	@GetMapping(value="/getProveedores/{rfcProveedor}/{rfcEmpresa}")
	public List<Proveedor> getPossibleSuppliers(@PathVariable String rfcProveedor, @PathVariable String rfcEmpresa){
		Empresa empresa = empresaRepository.findByRfc(rfcEmpresa);
		return proveedorRepository.findAllByEmpresasAndRfc(empresa, rfcProveedor);
	}
	
	@GetMapping(value="/log")
	public String getMovimientos(){
		return jsonReader.readLog();
	}
	
}
