package com.canfer.app.controller;

import java.io.IOException;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.UserDTO;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;
import com.canfer.app.service.EmpresaService;
import com.canfer.app.service.UsuarioService;
import com.canfer.app.storage.LogoStorageService;

import javassist.NotFoundException;


@Controller
public class PortalAdministrativoController {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private EmpresaRepository empresaRepo; 
	@Autowired
	private LogoStorageService logoStorageService;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	

	@GetMapping(value = "/documentosFiscalesClient")
	public String getModuloDocumentosFiscales(@RequestParam String rfc, Model model, RedirectAttributes ra) {
		
		// getting the authenticated user
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		Empresa company = empresaRepo.findByRfc(rfc);
		
		if (company == null) {
			
			ra.addFlashAttribute("errorCompany", true);
			return "redirect:/dashboard";
			
		}
		
		// check if the user is an admin
		if (loggedPrincipal.isAdmin()) {
			model.addAttribute("selectedCompany", rfc);
			model.addAttribute("companyProfile", company.getProfilePictureName());
			return "documentos-fiscales";
		}
		
		// if the user is not ADMIN, we need to check if it has access to that company.
		if (loggedPrincipal.getEmpresasRfc().contains(rfc)) {
			
			model.addAttribute("selectedCompany", rfc);
			model.addAttribute("companyProfile", company.getProfilePictureName());
			
			return "documentos-fiscales";
			
		} else {
			
			ra.addFlashAttribute("errorCompany", true);
			
			return "redirect:/dashboard";
		}

		
		
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
	

}
