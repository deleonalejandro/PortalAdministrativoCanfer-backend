package com.canfer.app.controller;


import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.ProveedorDTO;
import com.canfer.app.model.Log;
import com.canfer.app.repository.EstadoRepository;
import com.canfer.app.service.EmpresaService;
import com.canfer.app.service.ProveedorService;

import javassist.NotFoundException;


@Controller
@RequestMapping("/admin")
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private EstadoRepository estadoRepository; 
	
	public ProveedorController() {
	}
	
	@GetMapping("/suppliers")
	public String getSuppliers(Model model) {
		model.addAttribute("estados", estadoRepository.findAll());
		model.addAttribute("empresas", empresaService.findAll());
		return "suppliers-catalog";
	}
	
	@GetMapping(value = "/addSupplier")
	public String getSupplierForm(Model model) {
		model.addAttribute("supplier", new ProveedorDTO());
		model.addAttribute("empresas", empresaService.findAll());
		model.addAttribute("estados", estadoRepository.findAll());
		
		return "crear-proveedor";
	}
	
	@PostMapping(value = "/addSupplier")
	public String addSupplier(@ModelAttribute("supplier") ProveedorDTO proveedor, RedirectAttributes ra) {
		try {
			
			proveedorService.save(proveedor);
			
		} catch (EntityExistsException e) {
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/addSupplier";
		} catch (NullArgumentException e) {
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/addSupplier";
		} catch (UnknownError e) {
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/admin/addSupplier";
		} 
		
		return "redirect:/admin/suppliers";
	}
	
	@PostMapping(value = "/supplier/save")
	public String saveSupplier(ProveedorDTO proveedor, RedirectAttributes redirectAttributes) {
		try {
			proveedorService.save(proveedor);
		} catch (EntityExistsException e) {
			Log.falla("Error añadir proveedor: " + e.getMessage(), "ERROR_DB");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (UnknownError e) {
			Log.falla("Error añadir proveedor: " + e.getMessage(), "ERROR");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (NullArgumentException e) {
			Log.falla("Error añadir proveedor: " + e.getMessage(), "ERROR_STORAGE");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/admin/suppliers";
	}
	
	@PostMapping(value = "/supplier/update")
	public String updateSupplier(ProveedorDTO proveedor, RedirectAttributes redirectAttributes) {
		try {
			proveedorService.updateProveedor(proveedor);
		
		} catch (EntityNotFoundException e) {
			Log.falla("Error al actualizar la información: " + e.getMessage(), "ERROR_DB");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (UnknownError e) {
			Log.falla("Error al actualizar la información: " + e.getMessage(), "ERROR");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} 
		return "redirect:/admin/suppliers";
	}
	
	@GetMapping(value = "/supplier/delete/{id}")
	public String deleteSupplier(@PathVariable Long id, RedirectAttributes ra) {
		try {
			proveedorService.delete(id);
				
		} catch (NotFoundException e) {
			ra.addFlashAttribute("supplierNotFound", e.getMessage());
			return "redirect:/admin/suppliers";
		}
		return "redirect:/admin/suppliers";
	}

}
