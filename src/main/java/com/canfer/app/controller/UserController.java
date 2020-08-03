package com.canfer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




import com.canfer.app.model.UserDTO;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.UsuarioRepository;



@Controller
public class UserController {
	
	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserController() {
		//Constructor del controlador
	}
	
	@GetMapping(value = "/addUser")
	public String userForm(Model model)  {
        model.addAttribute("user", new UserDTO());
        return "crear-usuario";
    }
	
	@PostMapping(value = "/addUser")
	public String addUser(@ModelAttribute("user") UserDTO user) {
		//Data Transfer Object to pass the information between the user input and the database
		
		//Compare if the password and the confirmation are the same
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			return "crear-usuario";
		}
		
		//Before creating the Usuario object, we (e)ncode the information
		String ePassword = passwordEncoder.encode(user.getPassword());
		String eCorreo = passwordEncoder.encode(user.getCorreo());
		
		//Join the list using comma
		String permisos = String.join(",", user.getPermisos());
		
		//We create a user object from the Entity class Usuario.java
		Usuario usuario = new Usuario(user.getUsername(), ePassword, user.getNombre(), user.getApellido(), eCorreo, user.getRol(), permisos);

		//We save the new user in the database
		userRepository.save(usuario);
		
		return "redirect:/users";
	}
	
	@GetMapping(value = "/users")
	public String getUsers(Model model) {
		//Regreso los usuarios al DOM
		model.addAttribute("list", userRepository.findAll());
		
		return "users";
	}

}
