package com.canfer.app;







import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.canfer.app.model.Permisos;
import com.canfer.app.model.Roles;
import com.canfer.app.model.UserDTO;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.PermisosRepository;
import com.canfer.app.repository.RolesRepository;
import com.canfer.app.repository.UsuarioRepository;


@Controller
public class UserController {
	
	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private PermisosRepository permisosRepository;
	
	
	public UserController() {
		//Constructor del controlador
	}
	
	@GetMapping(value = "/addUser")
	public String userForm(Model model)  {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("rolesList", rolesRepository.findAll());
        model.addAttribute("permisosList", permisosRepository.findAll());
        return "crear-usuario";
    }
	
	@PostMapping(value = "/addUser")
	public String addUser(@ModelAttribute("user") UserDTO user) {
		//We use a Data Transfer Object to pass the information between the user input and the database
		//We create a user object from the Entity class Usuario.java
		Usuario usuario = new Usuario(user.getUsername(), user.getPassword(), user.getNombre(), user.getApellido(), user.getCorreo());
		
		//The role is find by name
		Roles rol =  rolesRepository.findByRol(user.getRol());
		usuario.setRol(rol);
		
		//The permissions are created on a list
		List <Permisos> permisos;
		permisos = permisosRepository.findAllById(user.getPermisos());
		usuario.setPermisos(permisos);

		//We save the new user in the database
		userRepository.save(usuario);
		
		return "redirect:/users";
	}
	
	@GetMapping(value = "/testAddUser")
	public String testAddUser() {
		Usuario userUsuario = new Usuario("alex", "hola", "alex", "hola", "chiqui");
		Roles rol;
		rol = rolesRepository.findByRol("ADMIN");
		userUsuario.setRol(rol);
		userRepository.save(userUsuario);
		return "index";
	}
	
	@GetMapping(value = "/users")
	public String users(Model model) {
		model.addAttribute("list", userRepository.findAll());
		
		return "users";
	}

}
