package com.canfer.app.controller;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.EmpresaDTO;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.MunicipioRepository;
import com.canfer.app.service.EmpresaService;


@Controller
@RequestMapping("/admin")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private EmpresaRepository empresaRepo;
	@Autowired
	private MunicipioRepository municipioRepository;
	
	public EmpresaController() {
	}
	
	@GetMapping("/companies")
	public String getCompanies(Model model) {
		model.addAttribute("empresas", empresaService.findAll());
		return "companies-catalog";
	}
	
	@GetMapping(value = "/addCompany")
	public String getCompanyForm(Model model) {
		model.addAttribute("company", new EmpresaDTO());
		model.addAttribute("estados", municipioRepository.findAll());
		
		return "crear-empresa";
	}
	
	@PostMapping(value = "/addCompany")
	public String addCompany(@ModelAttribute("company") EmpresaDTO empresa, RedirectAttributes ra) {
		try {
			empresaService.save(empresa);
		} catch (EntityExistsException e) {
			Log.falla("Error al añadir la empresa: " + e.getMessage());
			ra.addFlashAttribute("companyExistsError", e.getMessage());
			return "redirect:/admin/addCompany";
		} catch (NullArgumentException e) {
			Log.falla("Error al añadir la empresa: " + e.getMessage());
			ra.addFlashAttribute("nullValuesError", e.getMessage());
			return "redirect:/admin/addCompany";
		} catch (UnknownError e) {
			Log.falla("Error al añadir la empresa: " + e.getMessage());
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/addCompany";
		}
		
		return "redirect:/admin/companies";
	}
	
	@GetMapping(value = "/company/save/{id}")
	@ResponseBody
	public Empresa showCompany(@PathVariable Long id, Model model) {
		return empresaRepo.findById(id).get();
	}
	
	@PostMapping(value = "/company/save")
	public String saveCompany(EmpresaDTO company, RedirectAttributes redirectAttributes) {
		try {
			empresaService.save(company);
		} catch (EntityExistsException e) {
			Log.falla("Error al actualizar la información: " + e.getMessage());
			redirectAttributes.addFlashAttribute("updateError", e.getMessage());
		}
		return "redirect:/admin/companies";
	}
	
	@GetMapping(value = "/company/delete/{id}")
	public String deleteCompany(@PathVariable Long id, RedirectAttributes ra) {
		try {
			empresaService.delete(id);
		} catch (Exception e) {
			ra.addFlashAttribute("CompanyNotFoundMsg",e.getMessage());
		}
		return "redirect:/";
	}

}
