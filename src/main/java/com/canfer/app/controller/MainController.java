package com.canfer.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.canfer.app.security.AuthenticationFacade;

@Controller
public class MainController {

	@Autowired
	private AuthenticationFacade authenticationFacade;
	

	public MainController() {
		// Constructor
	}

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/login";
	}

	/*
	 * 
	 * LOGIN CONTROLLERS FOR PORTAL ADMINISTRATIVO
	 * 
	 */
	
	// Login form
	@RequestMapping("/login")
	public String login( Model model) {
		
		if (isAuthenticated()) {
	        return "redirect:/dashboard";
	    }
		
	    return "login";
		
	}

	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model, @RequestParam("auth") String authentication ) {
		
		model.addAttribute("loginError", true);
		
		if (authentication.equalsIgnoreCase("pa")) {
			
			return "login";
			
		} else {
			
			return "login-proveedores";
		}
	}
	
	@RequestMapping("/logoutSuccess")
	public String logout(Model model, @RequestParam("auth") String module) {
		
		model.addAttribute("success", true);
		
		if (module.equalsIgnoreCase("pa")) {
			
			return "login";
			
		} else {
			
			return "login-proveedores";
		}
		
	}
	
	/*
	 * 
	 * LOGIN CONTROLLERS FOR PORTAL PROVEEDORES
	 * 
	 */
	
	@GetMapping(value = "/login/portalP")
	public String getLoginProveedores() {
		
		if (isAuthenticated()) {
	        return "redirect:/dashboardSupplier";
	    }
		
		return "login-proveedores";
	}
	

	
	@GetMapping(value = "/cajaChicaClient")
	public String getModuloCajaChica() {
		
		return "caja-chica";
	}

	


	private boolean isAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			        return false;
			    }
	    return authentication.isAuthenticated();
	}





}
