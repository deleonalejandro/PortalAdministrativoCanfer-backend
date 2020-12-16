package com.canfer.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.EmpresaDTO;
import com.canfer.app.model.Consecutivo;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Municipio;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.EstadoRepository;
import com.canfer.app.repository.MunicipioRepository;
import com.canfer.app.service.EmpresaService;
import com.canfer.app.storage.LogoStorageService;
import com.canfer.app.storage.StorageException;

import javassist.NotFoundException;


@Controller
@RequestMapping("/admin")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private EmpresaRepository empresaRepo;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private LogoStorageService logoService;
	@Autowired
	private ConsecutivoRepository consecutivoRepository;
	
	public EmpresaController() {
	}
	
	@GetMapping("/companies")
	public String getCompanies(Model model) {
		model.addAttribute("empresas", empresaService.findAll());
		model.addAttribute("estados", estadoRepository.findAll());
		return "companies-catalog";
	}
	
	@GetMapping(value = "/addCompany")
	public String getCompanyForm(Model model) {
		model.addAttribute("company", new EmpresaDTO());
		model.addAttribute("estados", estadoRepository.findAll());
		
		return "crear-empresa";
	}
	
	
	@PostMapping(value = "/addCompany")
	public String addCompany(@ModelAttribute("company") EmpresaDTO empresa, MultipartFile logo, RedirectAttributes ra) {
		try {
			
			Empresa saveEmpresa;
			
			// add the picture name to the dto for saving into db
			empresa.setProfilePictureName(logo.getOriginalFilename());
			
			saveEmpresa = empresaService.save(empresa);
			
			// creating the sequence for the company
			Consecutivo consecutivo = new Consecutivo(saveEmpresa, null, "Documentos Fiscales", 0L, 9999999L, 0L);
			consecutivoRepository.save(consecutivo);
			
			Log.activity("Se creó una nueva empresa." , empresa.getNombre(), "NEW_USER");
		} catch (EntityExistsException e) {
			
			Log.falla("Error al añadir la empresa: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("companyExistsError", e.getMessage());
			return "redirect:/admin/addCompany";
			
		} catch (NullArgumentException e) {
			
			Log.falla("Error al añadir la empresa: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("nullValuesError", e.getMessage());
			return "redirect:/admin/addCompany";
			
		} catch (UnknownError e) {
			
			Log.falla("Error al añadir la empresa: " + e.getMessage(), "ERROR");
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/addCompany";
		}
		
		try {
			
			logoService.init();
			logoService.store(logo);
			
		} catch (StorageException e) {
			Log.falla("Error al añadir la empresa: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("logoError", e.getMessage());
			return "redirect:/admin/addCompany";
		}
		
		return "redirect:/admin/companies";
	}
	
	@GetMapping(value = "/company/save/{id}")
	@ResponseBody
	public Empresa showCompany(@PathVariable Long id, Model model) {
		return empresaRepo.findById(id).get();
	}
	
	@PostMapping(value = "/company/update")
	public String updateCompany(EmpresaDTO company, MultipartFile logo, RedirectAttributes redirectAttributes) {
		Path file = null;
		
		// check if new logo is present
		if (!logo.isEmpty()) {
			
			//delete actual logo 
			try {
				Empresa empresa = empresaService.findById(company.getIdEmpresa());
				file = logoService.load(empresa.getProfilePictureName());
				if (file.toFile().exists()) {
					
					// delete file if exists
					Files.delete(file);
					
				}
				
			} catch (IOException e) {
				Log.falla("No se logró eliminar el archivo " + file.getFileName() + ".", "ERROR_STORAGE");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("logoError", "Ocurrio un error inesperado: No se pudo eliminar el logotipo anterior");
			}
			
			//save new logo
			try {
				
				logoService.init();
				logoService.store(logo);
				
			} catch (StorageException e) {
				Log.falla("Error al añadir la empresa: " + e.getMessage(), "ERROR_DB");
				redirectAttributes.addFlashAttribute("logoError", e.getMessage());
				return "redirect:/admin/companies";
			}
			
			company.setProfilePictureName(logo.getOriginalFilename());
		}
		
		try {

			empresaService.update(company);
			Log.activity("Se actualizó la información de la empresa.", company.getNombre(), "UPDATE");

		} catch (EntityExistsException e) {
			Log.falla("Error al actualizar la información: " + e.getMessage(), "ERROR_DB");
			redirectAttributes.addFlashAttribute("updateError", e.getMessage());
		}
		
		return "redirect:/admin/companies";
	}
	
	@GetMapping(value = "/company/delete/{id}")
	public String deleteCompany(@PathVariable Long id, RedirectAttributes ra) {
		Path file = null;
		Empresa company = null;
		
		try {
			company = empresaService.findById(id);
			empresaService.delete(id);
			
			
		} catch (NotFoundException e) {
			ra.addFlashAttribute("CompanyNotFoundMsg", "Ocurrio un error inesperado: No se pudo eliminar la empresa");
		}
		
		try {
			
			file = logoService.load(company.getProfilePictureName());
			if (file.toFile().exists()) {
				
				// delete file if exists
				Files.delete(file);	
				
			}
			
		} catch (IOException e) {
			Log.falla("No se logró eliminar el archivo " + file.getFileName() + ".", "ERROR_STORAGE");
		} catch (Exception e) {
			ra.addFlashAttribute("logoError", "Ocurrio un error inesperado: No se pudo eliminar la empresa");
		}
		
		return "redirect:/admin/companies";
	}
	
	@GetMapping(value = "/findMunicipios/{idEstado}")
	@ResponseBody
	public List<Municipio> getMunicipios(@PathVariable Long idEstado) {
		return municipioRepository.findAllByEstado(estadoRepository.findById(idEstado).get());
	}
	
	

}
