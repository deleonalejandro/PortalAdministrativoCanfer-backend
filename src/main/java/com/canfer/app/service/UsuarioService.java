package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Proveedor;
import com.canfer.app.dto.UserDTO;
import com.canfer.app.model.Usuario;
import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.canfer.app.model.Usuario.UsuarioProveedor;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.UsuarioCanferRepository;
import com.canfer.app.repository.UsuarioProveedorRepository;
import com.canfer.app.repository.UsuarioRepository;

import javassist.NotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioCanferRepository usuarioCanferRepository;
	@Autowired
	private UsuarioProveedorRepository usuarioProveedorRepository;

	
	
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
	
	// TODO add more try catch to handle unexpected errors
	public Usuario save(UserDTO user) throws NotFoundException {
		
		Usuario testUsuario;
		Proveedor testProveedor;
		String ePassword;
		List<Empresa> empresas = empresaService.findAllById(user.getEmpresaIdsList());
		//TODO finish first with the CANFER modules
		Empresa empresaCreadora = empresaService.findById(1L);
		
		// we  check if the user already exists.
		testUsuario = usuarioRepository.findByUsername(user.getUsername());
		if (testUsuario != null) {
			throw new UsernameNotFoundException("El usuario: " + user.getUsername() + " ya existe.");
		}
		
		// first check if we want to create a company user or provider user.
		if (user.getRfc() == null) {		
			
			if (user.getRol().isEmpty()) {
				throw new EmptyResultDataAccessException("El usuario debe tener un rol asignado.", 1);
			}
			
			ePassword = passwordEncoder.encode(user.getPassword());
			
			UsuarioCanfer usuario = new UsuarioCanfer(user.getUsername(), ePassword,
					user.getNombre(), user.getApellido(), user.getCorreo(), user.getRol(), user.getPermisosToString());
			
			// assign the companies that the user will manage
			usuario.setEmpresas(empresas);
			
			return usuarioCanferRepository.save(usuario);
			
		} else {
			
			testProveedor = proveedorRepository.findByEmpresasAndRfc(empresaCreadora, user.getRfc());
			if (testProveedor == null) {
				throw new UsernameNotFoundException("El proveedor no es valido. Verifique el RFC");
			}
			
			ePassword = passwordEncoder.encode(user.getPassword());
			
			UsuarioProveedor usuario = new UsuarioProveedor(user.getUsername(), ePassword,
					user.getNombre(), user.getApellido(), user.getCorreo(), "USUARIO_PROVEEDOR", user.getPermisosToString());
			
			usuario.setProveedor(testProveedor);
			
			return usuarioProveedorRepository.save(usuario);

		}
		
	}
	
	// TODO add more try catch to handle unexpected errors
	public Usuario update(UserDTO user) {
		//Create the object user, that will be updated in the DB.
		Optional<Usuario> checkUsuario = usuarioRepository.findById(user.getUserId());
		if (checkUsuario.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no existe");
		}
		//Take the value of the object if exists
		Usuario updateUsuario = checkUsuario.get();
		
		//Use setters to transfer the basic information, except password.
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
			throw new UsernameNotFoundException("El usuario no existe.");
		}
		
		usuarioRepository.delete(deleteUsuario.get());
	}
	
	

}
