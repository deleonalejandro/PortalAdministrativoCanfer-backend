package com.canfer.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.canfer.app.service.EmpresaService;

@Controller
public class MainController {
	
	@Autowired
	private EmpresaService empresaService;

	public MainController() {
		// Constructor
	}

	@GetMapping(value = "/")
	public String home() {
		return "index";
	}

	// Login form
	@RequestMapping("/login")
	public String login() {
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

	@GetMapping(value = "/documentosFiscalesClient")
	public String getModuloDocumentosFiscales() {
		/*
		//create cookie
		Cookie empresaCookie = new Cookie("empresa", idEmpresa);
		//add cookie
		response.addCookie(empresaCookie);*/
		
		return "documentos-fiscales";
	}
	
	@GetMapping(value = "/dashboard")
	public String getDashboard(Model model) {
		model.addAttribute("empresa", empresaService.findAll());
		return "dashboard-2";
	}

}
