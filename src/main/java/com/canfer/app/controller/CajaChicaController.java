package com.canfer.app.controller;

 

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Sucursal;
import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;
import com.canfer.app.service.RepositoryService;
import com.canfer.app.storage.LogoStorageService;
 


@Controller
@RequestMapping("/cajachicaclient")
public class CajaChicaController {
	
	@Autowired
	private LogoStorageService logoStorageService;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	private RepositoryService superRepo;
	

	@GetMapping("/home")
	public String getModuloCajaChica(@CookieValue("suc") Long idSucursal, Model model) {
		
		Empresa company;
		
		Optional<Sucursal> sucursal = superRepo.findSucursalById(idSucursal);
		
		if (sucursal.isPresent()) {
			company = sucursal.get().getEmpresa();
			model.addAttribute("companyProfile", company.getProfilePictureName());
			
		}

		model.addAttribute("clasificaciones", superRepo.findAllClasificacionCajaChicas());
		
		//TODO RETURN USER IF NOT PART OF SUCURSAL
		
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
	
	@GetMapping("/administration")
	public String getAdministrationCajaChica(@CookieValue("suc") Long idSucursal, Model model) {
		
		Empresa company;
		
		Optional<Sucursal> sucursal = superRepo.findSucursalById(idSucursal);
		
		if (sucursal.isPresent()) {
			company = sucursal.get().getEmpresa();
			model.addAttribute("companyProfile", company.getProfilePictureName());
			
		}
		
		return "caja-chica-administration";
	}
	/*
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
		
	} */
	
	@GetMapping(value = "/dashboard")
	public String getDashboard(Model model) {
		
		UsuarioCanfer canferUser;
		List<Sucursal> sucursales;
		
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		
		canferUser = (UsuarioCanfer) loggedPrincipal.getUsuario();
				
		if (canferUser.isAdmin()) {
			
			sucursales = superRepo.findAllSucursales();
			
		} else if (canferUser.isContador()) {
			
			List<Empresa> companies = superRepo.findAllEmpresaById(canferUser.getEmpresasId());
			
			sucursales = superRepo.findAllSucursalByEmpresaIn(companies);
			
		} else {
			
			sucursales = superRepo.findAllSucursalByUsuario(canferUser);
		
		}
	
	    HashMap<Sucursal, Empresa> sucursalAndCompany = new HashMap<>();
	    
		for (Sucursal sucursal: sucursales) {
			
			sucursalAndCompany.put(sucursal, sucursal.getEmpresa());
			
		}
		
		model.addAttribute("suco", sucursalAndCompany);
		
		return "dashboard-cajachica";
	}
	
	
	@GetMapping("/company/profile/{name}")
	public ResponseEntity<Resource> showProfileImage(@PathVariable String name) throws IOException {
		
		Resource resource = logoStorageService.loadAsResource(name);
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("image/png"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
