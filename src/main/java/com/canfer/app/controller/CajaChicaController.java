package com.canfer.app.controller;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 
 
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.service.EmpresaService;
import com.canfer.app.service.RepositoryService;
import com.canfer.app.storage.LogoStorageService;
 


@Controller
public class CajaChicaController {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private EmpresaRepository empresaRepo; 
	@Autowired
	private LogoStorageService logoStorageService;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	private RepositoryService superRepo;
	

	@GetMapping(value = "/cajachicaclient")
	public String getModuloCajaChica(Model model) {
		
		model.addAttribute("selectedCompany", "PAE920709D75");
		model.addAttribute("selectedClave", "A-0080");
		/*
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
		}*/
		
		return "caja-chica";

	}
	
	/*************************** CHANGE METHODS ******************************
	@GetMapping("/documentosFiscalesClient/catalogo")
	public String getCatalog(@RequestParam String selectedCompany, Model model, RedirectAttributes ra) {
		
		Empresa company = empresaRepo.findByRfc(selectedCompany);
		
		if (company == null) {
			ra.addFlashAttribute("errorCompany", true);
			return "redirect:/dashboard";
		}
		
		// getting the authenticated user
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		
		
		
		// check if the user is an admin
		if (loggedPrincipal.isAdmin()) {
			model.addAttribute("selectedCompany", selectedCompany);
			model.addAttribute("companyProfile", company.getProfilePictureName());
			return "catalog-df";
		}
		
		// if the user is not ADMIN, we need to check if it has access to that company.
		if (loggedPrincipal.getEmpresasRfc().contains(selectedCompany)) {
			
			model.addAttribute("selectedCompany", selectedCompany);
			model.addAttribute("companyProfile", company.getProfilePictureName());
			
			return "catalog-df";
			
		} else {
			
			ra.addFlashAttribute("errorCompany", true);
			
			return "redirect:/dashboard";
		}
		
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
	
	*/

}
