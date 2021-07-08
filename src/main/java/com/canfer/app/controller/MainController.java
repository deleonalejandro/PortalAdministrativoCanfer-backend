package com.canfer.app.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.canfer.app.model.GenericResponse;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;
import com.canfer.app.service.EmpresaService;
import com.canfer.app.service.RepositoryService;

import javassist.expr.NewArray;

@Controller
public class MainController {

	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@Autowired
	private ComprobanteFiscalRespository cfRepo;
	
	@Autowired
	private RepositoryService superRepo;
	
	@Autowired
	private EmpresaService empresaService;
	

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
		
		Usuario user;
		
		if (isAuthenticated()) {
			
			UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
			
			user = (Usuario) loggedPrincipal.getUsuario();
			
			if (user.isAdmin()) {
				return "redirect:/admin/cpanel";
				
			} else if (user.isContador()) {
				return "redirect:/dashboard";
				
			} else if (user.isProveedor()) {
				return "redirect:/dashboardSupplier";
			}
	    }
		
		model.addAttribute("empresas", empresaService.findAll());
	    return "login";
		
	}

	// Login form with error
	@RequestMapping("/login-error")
	public String loginError(Model model, @RequestParam("auth") String authentication ) {
		
		model.addAttribute("loginError", true);
		
		if (authentication.equalsIgnoreCase("pa")) {
			model.addAttribute("empresas", empresaService.findAll());
			return "login";
			
		} else {
			
			return "login-proveedores";
		}
	}
	
	@RequestMapping("/logoutSuccess")
	public String logout(Model model, @RequestParam("auth") String module) {
		
		model.addAttribute("success", true);
		
		if (module.equalsIgnoreCase("pa")) {
			model.addAttribute("empresas", empresaService.findAll());
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
	

	/* TODO ERASE THIS METHOD AFTER TESTINS SP
	 * 
	 * --------------------------------------
	 * CHECK FOR THE PARAMETER'S NAMES MOST BE
	 * EXACTLY THE SAME.
	 * 
	 */
	
	@GetMapping(value = "/testsp")
	@ResponseBody
	public String testSp(@RequestParam String n) {
		
		String pServidor = "192.168.1.132";
		String pBaseDatos = "PAEPRODUCCION";
		Long pIdEmpresa = 8L;
		
		cfRepo.actualizaBitRs(pServidor, pBaseDatos, pIdEmpresa);
		
		return "true";
	}
	
	@GetMapping(value = "/TEST2")
	public ResponseEntity<GenericResponse> test2() {
		
		return new ResponseEntity<>(new GenericResponse("Esto es una prueba cool", true), HttpStatus.OK);
	}
	
	/*
	 * ENDLINE 
	 * ---------------------------------------
	 */

	@GetMapping("/sucursal")
	@ResponseBody
	public LocalDateTime testSucursal() {
		
		LocalDateTime dateTime = LocalDateTime.parse("18/02/2021",
		        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		return dateTime;
	}
	


	private boolean isAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			        return false;
			    }
	    return authentication.isAuthenticated();
	}
	
	





}
