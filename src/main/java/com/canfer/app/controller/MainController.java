package com.canfer.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
	
	@RequestMapping("/logoutSuccess")
	public String logout(Model model) {
		model.addAttribute("success", true);
		return "login";
	}
	
	/*
	 * 
	 * LOGIN CONTROLLERS FOR PORTAL PROVEEDORES
	 * 
	 */
	
	@GetMapping(value = "/login/portalP")
	public String getLoginProveedores() {
		
		return "login-proveedores";
	}

	


	private boolean isAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			        return false;
			    }
	    return authentication.isAuthenticated();
	}




}
