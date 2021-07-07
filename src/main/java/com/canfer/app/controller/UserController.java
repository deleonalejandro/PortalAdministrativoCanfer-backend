package com.canfer.app.controller;

import java.sql.SQLDataException;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.UserDTO;
import com.canfer.app.model.Log;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.UsuarioRepository;
import com.canfer.app.service.EmpresaService;
import com.canfer.app.service.UsuarioService;

import javassist.NotFoundException;




@Controller
@RequestMapping(value = "/admin")
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioRepository userRepository;
	private static final String USER_SIGNUP = "crear-usuario" ;

	
	public UserController() {
	}
	
	@GetMapping(value = "/users")
	public String getUsers(Model model) {
		//Regreso los usuarios al DOM
		model.addAttribute("list", userRepository.findAll());
		model.addAttribute("empresas", empresaService.findAll());
		return "users-catalog";
	}
	
	
	@GetMapping(value = "/addUser")
	public String getUserForm(Model model)  {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("empresas", empresaService.findAll());
        return USER_SIGNUP;
    }
	
	@PostMapping(value = "/addUser")
	public String addUser(@ModelAttribute("user") UserDTO user, RedirectAttributes ra) {
		try {
			//Compare if the password and the confirmation are the same
			if (!user.getPassword().equals(user.getConfirmPassword())) {
				ra.addFlashAttribute("passError", "Las contraseñas no coinciden, vuelve a intentarlo.");
				return USER_SIGNUP;
			}
		
			usuarioService.save(user);
		
		} catch (UsernameNotFoundException | EmptyResultDataAccessException e) {
			Log.falla("Error al crear usuario: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("errorMessage", e.getMessage());
			return USER_SIGNUP;
		} catch (NotFoundException e) {
			// company not identified
			Log.falla("Error al crear usuario: "+user.getUsername(), "ERROR_DB");
			e.printStackTrace();
		}
				
		return "redirect:/admin/users";
	}
	
	// this method returns a user object in a JSON format.
	@GetMapping(value = "/user/save/{id}")
	@ResponseBody
	public Usuario showUser(@PathVariable Long id, Model model) {
		
		return usuarioService.findById(id);
	}
	
	@PostMapping(value = "/user/save")
	public String saveUser(UserDTO user, RedirectAttributes ra) {
		try {
			usuarioService.update(user);
			
		} catch (UsernameNotFoundException e) {
			Log.falla("Error al actualizar usuario: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("updateError", e.getMessage());
		}
		return "redirect:/admin/users";
	}
	
	@GetMapping(value = "/user/delete/{id}")
	public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
		String response = "";
		try {
			
			response = usuarioService.delete(id);
			
		} catch (UsernameNotFoundException e) {
			Log.falla("Error al actualizar usuario: " + e.getMessage(),"ERROR_DB");
			ra.addFlashAttribute("UserNotFoundMessage", e.getMessage());
		}
		
		if (response.equalsIgnoreCase("USUARIO_EXTERNAL")) {
			return "redirect:/admin/suppliersUsers";
		} else {
			return "redirect:/admin/users";
		}
	}
	
	@PostMapping(value = "/user/resetpassword")
	public ResponseEntity<String> resetUserPassword(UserDTO user) throws JSONException {
		JSONObject response = new JSONObject();
		try {
			if(usuarioService.changeUserPassword(user)) {
				response.put("status", true);
				response.put("desc", "La contraseña se cambio exitosamente.");
			}
		} catch (UsernameNotFoundException e) {
			Log.falla("Error al actualizar contraseña: " + e.getMessage(), "ERROR_DB");
			response.put("status", false);
			response.put("desc", "Error al actualizar contraseña: " + e.getMessage());
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/user/supplier/resetpassword")
	public ResponseEntity<String> resetUserExternalPassword(UserDTO user) throws JSONException {
		JSONObject response = new JSONObject();
		try {
			if(usuarioService.changeUserExternalPassword(user)) {
				response.put("status", true);
				response.put("desc", "La contraseña se cambio exitosamente.");
			}
		} catch (UsernameNotFoundException e) {
			Log.falla("Error al actualizar contraseña: " + e.getMessage(), "ERROR_DB");
			response.put("status", false);
			response.put("desc", "Error al actualizar contraseña: " + e.getMessage());
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/user/proveedor/save")
	public String saveUserProveedor(UserDTO user, RedirectAttributes ra) {
		try {
			usuarioService.updateSupplier(user);
		} catch (UsernameNotFoundException e) {
			Log.falla("Error al actualizar usuario: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("updateError", e.getMessage());
		}
		return "redirect:/admin/suppliersUsers";
	}
	
	@GetMapping(value = "/suppliersUsers")
	public String getSuppliersUsers(Model model) {
		//Regreso los usuarios al DOM
		model.addAttribute("list", userRepository.findAll());
		model.addAttribute("empresas", empresaService.findAll());
		return "users-supplier-catalog";
	}
	
	@GetMapping(value = "/addUserSupplier")
	public String addUserSupplier(Model model) {
		
		model.addAttribute("user", new UserDTO());
		
		return "nueva-cuenta-proveedor-admin";
	}
	
	@PostMapping(value = "/addUserSupplier")
	public String saveAndRegisterUserSupplier(@ModelAttribute("user") UserDTO user, RedirectAttributes ra) {
		
		try {
			
			usuarioService.saveUsuarioProveedor(user);
			
		} catch (EntityExistsException | NotFoundException e) {
			Log.falla("Error al registrar el usuario proveedor: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/registerSupplier";
		} catch (SQLDataException e) {
			Log.falla("Error inesperado al registrar el usuario proveedor.", "ERROR_DB");
			return "redirect:/registerSupplier";
		}
		
		ra.addFlashAttribute("registerSuccess", true);
		return "redirect:/admin/users";
	}
	
	
	
}
