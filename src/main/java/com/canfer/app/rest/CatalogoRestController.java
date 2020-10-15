package com.canfer.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.UsuarioRepository;

@RestController
@RequestMapping("/catalogsAPI")
public class CatalogoRestController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;
	
	public CatalogoRestController() {
		// Este es el constructor vacio del controlador
	}
	
	@GetMapping(value = "/getUsers")
	public List<Usuario> getAllUsers(){
		return usuarioRepository.findAll();
	}
	
	@GetMapping(value = "/getSuppliers")
	public List<Proveedor> getAllSuppliers(){
		return proveedorRepository.findAll();
	}

	
}
