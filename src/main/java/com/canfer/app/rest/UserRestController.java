package com.canfer.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.Usuario;
import com.canfer.app.repository.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuarios")
public class UserRestController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public UserRestController() {
		// Este es el constructor vacio del controlador
	}
	
	@GetMapping(value = "/getUsers")
	public List<Usuario> getAllUsers(){
		return usuarioRepository.findAll();
	}

	
}
