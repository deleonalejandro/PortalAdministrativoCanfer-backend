package com.canfer.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.UserDTO;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario.UsuarioProveedor;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.UsuarioProveedorRepository;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;
import com.canfer.app.service.RepositoryService;
import com.canfer.app.service.UsuarioService;

import javassist.NotFoundException;


@Controller
public class PortalProveedorController {
	
	@Autowired
	private EmpresaRepository empresaRepo;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioProveedorRepository usuarioProveedorRepository;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	private RepositoryService superRepo; 

	
	@GetMapping(value = "/proveedoresClient")
	public String getModuloProveedores(@RequestParam("rfc") String rfc, @RequestParam("clv") String claveProv, Model model, RedirectAttributes ra) {
		
		List<String> claves = new ArrayList<>();
		Empresa company = empresaRepo.findByRfc(rfc);
		
		if (company == null) {
			
			ra.addFlashAttribute("errorCompany", true);
			return "redirect:/dashboardSupplier";
			
		}
		
		// getting the authenticated user information
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		UsuarioProveedor user = usuarioProveedorRepository.findByUsername(loggedPrincipal.getUsername());
		
		user.getProveedores().forEach(proveedor -> claves.add(proveedor.getClaveProv()));
		
		Optional<Proveedor> proveedor = superRepo.findProveedorByEmpresaAndClaveProv(
				company, claveProv);
		
		if (claves.contains(claveProv)) {
			
			model.addAttribute("selectedCompany", rfc);
			model.addAttribute("selectedClave", claveProv);
			model.addAttribute("companyProfile", company.getProfilePictureName());
			model.addAttribute("company", company);
			
			if (proveedor.isPresent()) {
				
				model.addAttribute("supplier", proveedor.get());
				
			}
			
			return "proveedores";
			
		} else {
			
			ra.addFlashAttribute("errorCompany", true);
			
			return "redirect:/dashboardSupplier";
			
		}

	}
	
	@GetMapping(value = "/dashboardSupplier")
	public String getDashboardProveedor(Model model) {
		
		// getting the authenticated user
		UserPrincipal loggedPrincipal = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		UsuarioProveedor user = usuarioProveedorRepository.findByUsername(loggedPrincipal.getUsername());
		
		// Create a HashMap object called capitalCities
	    HashMap<String, Empresa> claveAndCompany = new HashMap<>();
	    
		for (Proveedor proveedor : user.getProveedores()) {
			
			claveAndCompany.put(proveedor.getClaveProv(), proveedor.getEmpresas().get(0));
			
		}
		
		//we add all companies
		model.addAttribute("claves", claveAndCompany);
		// we add the hash-map containing
		return "dashboard-proveedor";
	}
	
	@GetMapping(value = "/registerSupplier")
	public String registerSupplier(Model model) {
		
		model.addAttribute("user", new UserDTO());
		
		return "nueva-cuenta-proveedor";
	}
	
	@PostMapping(value = "/registerSupplier")
	public String saveAndRegisterSupplier(@ModelAttribute("user") UserDTO user, RedirectAttributes ra) {
		
		List<Proveedor> proveedores = superRepo.findAllProveedorByRfcAndBitActivo(user.getRfc(), true);
		
		for (Proveedor proveedor : proveedores) {
			
			List<UsuarioProveedor> usuarios = usuarioProveedorRepository.findAllByProveedores(proveedor);
			
			if (!usuarios.isEmpty()) {
				
				Log.general("Error al registrar el usuario proveedor: El RFC ya tiene una cuenta registrada.");
				ra.addFlashAttribute("errorMessage", "Este RFC ya se encuentra registrado.");
				return "redirect:/registerSupplier";
				
			}
		}
		

		try {
			
			usuarioService.saveUsuarioProveedor(user);
			
		} catch (EntityExistsException | NotFoundException e) {
			Log.falla("Error al registrar el usuario proveedor: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/registerSupplier";
		} 
		
		ra.addFlashAttribute("registerSuccess", true);
		return "redirect:/login/portalP";
	}

}
