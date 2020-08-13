package com.canfer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.canfer.app.model.UserDTO;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.UsuarioRepository;
import com.canfer.app.service.UsuarioService;




@Controller
//@RequestMapping(value = "/administration/users")
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository userRepository;
	private static final String USER_SIGNUP = "crear-usuario" ;

	
	public UserController() {
		//Constructor del controlador
	}
	
	@GetMapping(value = "/addUser")
	public String getUserForm(Model model)  {
        model.addAttribute("user", new UserDTO());
        return USER_SIGNUP;
    }
	
	@PostMapping(value = "/addUser")
	public String addUser(@ModelAttribute("user") UserDTO user, Model model) {
		try {
			//Compare if the password and the confirmation are the same
			if (!user.getPassword().equals(user.getConfirmPassword())) {
				return USER_SIGNUP;
			}
		
			usuarioService.save(user);
			
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return USER_SIGNUP;
		}
				
		return "redirect:/users";
	}
	
	@GetMapping(value = "/save/{id}")
	@ResponseBody
	public Usuario showUser(@PathVariable Long id, Model model) {
		return usuarioService.findById(id);
	}
	
	@PostMapping(value = "/save")
	public String saveUser(UserDTO user) {
		usuarioService.update(user);
		return "redirect:/users";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteUser(@PathVariable Long id, Model model) {
		try {
			usuarioService.delete(id);
		} catch (Exception e) {
			model.addAttribute("UserNotFoundMessage",e.getMessage());
			return "redirect:/users";
		}
		return "redirect:/users";
	}
	
	@GetMapping(value = "/users")
	public String getUsers(Model model) {
		//Regreso los usuarios al DOM
		model.addAttribute("list", userRepository.findAll());
		return "users-catalog";
	}
}
