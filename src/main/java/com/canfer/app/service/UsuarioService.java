package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.stereotype.Service;

import com.canfer.app.model.UserDTO;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario findById(Long id) {
		Optional<Usuario> userUsuario = usuarioRepository.findById(id);
		if (userUsuario.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no existe.");
		}
		return userUsuario.get(); 
	}
	
	public Usuario save(UserDTO user) {
		//We  check if the user already exists.
		Usuario testUsuario = usuarioRepository.findByUsername(user.getUsername());
		if (testUsuario != null) {
			throw new UsernameNotFoundException("El usuario: " + user.getUsername() + " ya existe.");
		}
		
		if (user.getRol().isEmpty()) {
			throw new EmptyResultDataAccessException("El usuario debe tener un rol asignado.", 1);
		}
		
		//Before creating the Usuario object, we (e)ncode the information
		String ePassword = passwordEncoder.encode(user.getPassword());
		

		//Create a user object from the Entity class Usuario.java
		Usuario usuario = new Usuario(user.getUsername(), ePassword,
				user.getNombre(), user.getApellido(), user.getCorreo(), user.getRol(), user.getPermisosToString());
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario update(UserDTO user) {
		//Create the object user, that will be updated in the DB.
		Optional<Usuario> checkUsuario = usuarioRepository.findById(user.getUserId());
		if (checkUsuario.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no existe");
		}
		//Take the value of the object if exists
		Usuario updateUsuario = checkUsuario.get();
		
		//Use setters to transfer the basic information, except password.
		updateUsuario.setIdUsuario(user.getUserId());
		updateUsuario.setUsername(user.getUsername());
		updateUsuario.setActivo(user.getActivo());
		updateUsuario.setApellido(user.getApellido());
		updateUsuario.setCorreo(user.getCorreo());
		updateUsuario.setNombre(user.getNombre());
		updateUsuario.setPermisos(user.getPermisosToString());
		updateUsuario.setRol(user.getRol());
		
		return usuarioRepository.save(updateUsuario);
	}
	
	public void delete(Long id) {
		//We  check if the user already exists.
		Optional<Usuario> deleteUsuario = usuarioRepository.findById(id);
		if (deleteUsuario.isEmpty()) {
			throw new UsernameNotFoundException("El usuario: " + deleteUsuario.get().getUsername() + " no existe.");
		}
		
		usuarioRepository.delete(deleteUsuario.get());
	}

}
