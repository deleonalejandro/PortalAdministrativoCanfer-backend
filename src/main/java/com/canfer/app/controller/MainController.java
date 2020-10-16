package com.canfer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.service.EmpresaService;
import com.canfer.app.webservice.sat.SatVerificacionService;
import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.pdfExport.CrystalReportService;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;

@Controller
public class MainController {
	
	@Autowired
    CrystalReportService crService;
	@Autowired
	EmailSenderService eSenderService; 
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	SatVerificacionService satserv;

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

	@GetMapping(value = "/documentosFiscalesClient")
	public String getModuloDocumentosFiscales(@RequestParam String rfc, Model model, RedirectAttributes ra) {
		
		// getting the authenticated user
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		
		// check if the user is an admin
		if (loggedPrincipal.isAdmin()) {
			model.addAttribute("selectedCompany", rfc);
			return "documentos-fiscales";
		}
		
		// if the user is not ADMIN, we need to check if it has access to that company.
		for (String userRfc : loggedPrincipal.getEmpresasRfc()) {
			if (userRfc.equalsIgnoreCase(rfc)) {
				model.addAttribute("selectedCompany", rfc);
				return "documentos-fiscales";
			}
		}
		
		ra.addFlashAttribute("errorCompany", true);
		
		return "redirect:/dashboard";
		
		/*
		//create cookie
		Cookie empresaCookie = new Cookie("empresa", idEmpresa);
		//add cookie
		response.addCookie(empresaCookie);*/
	}
	
	@GetMapping(value = "/dashboard")
	public String getDashboard(Model model) {
		
		return "dashboard-2";
	}
	

	private boolean isAuthenticated() {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.
			      isAssignableFrom(authentication.getClass())) {
			        return false;
			    }
	    return authentication.isAuthenticated();
	}
	

}
