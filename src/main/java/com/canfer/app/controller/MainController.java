package com.canfer.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.service.EmpresaService;
import com.canfer.app.storage.LogoStorageService;
import com.canfer.app.webservice.sat.SatVerificacionService;

import javassist.NotFoundException;

import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.model.Empresa;
import com.canfer.app.pdfExport.CrystalReportService;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;

@Controller
public class MainController {
	
	@Autowired
    private CrystalReportService crService;
	@Autowired
	private EmailSenderService eSenderService; 
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	private SatVerificacionService satserv;
	@Autowired
	private LogoStorageService logoStorageService;

	public MainController() {
		// Constructor
	}

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/login";
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
		model.addAttribute("empresas", empresaService.findAll());
		return "dashboard-2";
	}
	
	@GetMapping("/company/profile/{name}")
	public ResponseEntity<Resource> showProfileImage(@PathVariable String name) throws IOException {
		
		Resource resource = logoStorageService.loadAsResource(name);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("image/png"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping(value = "/cpanel")
	public String getCPanel() {
		
		return "admin-panel";
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
