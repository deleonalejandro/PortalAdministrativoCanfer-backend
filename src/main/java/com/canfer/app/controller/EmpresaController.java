package com.canfer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.canfer.app.model.EmpresaDTO;
import com.canfer.app.repository.MunicipioRepository;
import com.canfer.app.service.EmpresaService;


@Controller
@RequestMapping("/administration/companies")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private MunicipioRepository municipioRepository;
	private static final String ADD_COMPANY_PAGE = "crear-company";
	
	@GetMapping(value = "/")
	public String getCompanies(Model model) {
		model.addAttribute("companies", empresaService.findAll());
		return "companies-catalog";
	}
	
	@GetMapping(value = "/addCompany")
	public String getCompanyForm(Model model) {
		model.addAttribute("company", new EmpresaDTO());
		model.addAttribute("estados", municipioRepository.findAll());
		
		return ADD_COMPANY_PAGE;
	}
	
	@PostMapping(value = "/addCompany")
	public String addCompany(@ModelAttribute("company") EmpresaDTO empresa, Model model) {
		try {
			empresaService.save(empresa);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteCompany(@PathVariable Long id, Model model) {
		try {
			empresaService.delete(id);
		} catch (Exception e) {
			model.addAttribute("CompanyNotFoundMsg",e.getMessage());
			return "redirect:/";
		}
		return "redirect:/";
	}

}
