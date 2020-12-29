package com.canfer.app.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Proveedor;
import com.canfer.app.dto.UserDTO;
import com.canfer.app.mail.EmailSenderService;
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
	@Autowired
	private EmailSenderService emailSender; 

	public List<Usuario> findAll() {
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
		String ePassword;
		List<Empresa> empresas = empresaService.findAllById(user.getEmpresaIdsList());

		// we check if the user already exists.
		testUsuario = usuarioRepository.findByUsername(user.getUsername());
		if (testUsuario != null) {
			throw new UsernameNotFoundException("El usuario: " + user.getUsername() + " ya existe.");
		}

		if (user.getRol().isEmpty()) {
			throw new EmptyResultDataAccessException("El usuario debe tener un rol asignado.", 1);
		}

		ePassword = passwordEncoder.encode(user.getPassword());

		UsuarioCanfer usuario = new UsuarioCanfer(user.getUsername(), ePassword, user.getNombre(), user.getApellido(),
				user.getCorreo(), user.getRol(), user.getPermisosToString());

		// assign the companies that the user will manage
		usuario.setEmpresas(empresas);

		Log.falla("Se agregó un nuevo usuario: " + user.getUsername(), "NEW_USER");

		return usuarioCanferRepository.save(usuario);

	}

	public Usuario saveUsuarioProveedor(UserDTO user) throws NotFoundException {

		Usuario testProveedor;
		String ePassword;
		String password;
		List<Proveedor> proveedoresList;
		List<Empresa> empresas = new ArrayList<>();

		testProveedor = usuarioProveedorRepository.findByUsername(user.getUsername());
		proveedoresList = proveedorRepository.findAllByRfcAndBitActivo(user.getRfc(), true);

		if (testProveedor != null) {
			throw new EntityExistsException("El nombre de usuario ya existe. Escoge uno diferente.");
			
		}
		
		if (proveedoresList.isEmpty()) {
			throw new NotFoundException("El RFC para registrar la cuenta no es valido.");
		}

		password = generatePassword(user.getRfc());

		
		System.out.println(user.getRfc() + " " + password);
		ePassword = passwordEncoder.encode(password);

		UsuarioProveedor usuario = new UsuarioProveedor(user.getUsername(), ePassword, user.getNombre(),
				user.getApellido(), user.getCorreo(), "USER_PROVEEDOR", "");

		// assign the suppliers to the users
		usuario.setProveedores(proveedoresList);

		// lets get which companies this user is related to
		for (Proveedor proveedor : proveedoresList) {
			empresas.addAll(proveedor.getEmpresas());
		}

		usuario.setEmpresas(empresas);

		Log.falla("Se agregó un nuevo usuario: " + user.getUsername(), "NEW_USER");
		emailSender.sendEmailNewAccount(usuario, password);
		
		return usuarioProveedorRepository.save(usuario);
		

	}

	public Usuario updateUserSuppliers(UsuarioProveedor user) {

		List<Proveedor> proveedoresList;

		proveedoresList = proveedorRepository.findAllByRfcAndBitActivo(user.getUsername(), true);

		// assign the suppliers to the users
		user.setProveedores(proveedoresList);

		return usuarioProveedorRepository.save(user);
	}

	// TODO add more try catch to handle unexpected errors
	public Usuario update(UserDTO user) {
		// Create the object user, that will be updated in the DB.
		Optional<Usuario> checkUsuario = usuarioRepository.findById(user.getUserId());
		if (checkUsuario.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no existe");
		}
		// Take the value of the object if exists
		Usuario updateUsuario = checkUsuario.get();

		// Use setters to transfer the basic information, except password.
		updateUsuario.setUsername(user.getUsername());
		updateUsuario.setApellido(user.getApellido());
		updateUsuario.setCorreo(user.getCorreo());
		updateUsuario.setNombre(user.getNombre());
		updateUsuario.setPermisos(user.getPermisosToString());
		updateUsuario.setRol(user.getRol());

		if (user.getActivo() == null) {
			updateUsuario.setActivo(false);
		} else {
			updateUsuario.setActivo(user.getActivo());
		}
		
		Log.activity("Se actualizó el usuario " + user.getUsername() + ".",
				checkUsuario.get().getEmpresasNombre().toString(), "NEW_USER");

		return usuarioRepository.save(updateUsuario);
	}

	public void delete(Long id) {
		// We check if the user already exists.
		Optional<Usuario> deleteUsuario = usuarioRepository.findById(id);
		
		if (deleteUsuario.isEmpty()) {
			throw new UsernameNotFoundException("El usuario no existe.");
		}

		usuarioRepository.delete(deleteUsuario.get());

		Log.activity("Se eliminó al usuario " + deleteUsuario.get().getUsername() + ".",
				deleteUsuario.get().getEmpresasNombre().toString(), "DELETE_USER");
	}

	private String generatePassword(String rfc) {

		LocalDateTime todayDateTime = LocalDateTime.now();
		String pwd;

		pwd = rfc.substring(0, 4) + String.valueOf(todayDateTime.getHour()) + String.valueOf(todayDateTime.getMinute());

		return pwd;
	}

}
