package com.canfer.app.controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.dto.ComprobanteFiscalDTO;
import com.canfer.app.dto.ProveedorDTO;
import com.canfer.app.dto.UserDTO;
import com.canfer.app.model.DocumentosNacionalesActions;
import com.canfer.app.model.Log;
import com.canfer.app.storage.ComprobanteStorageService;

import javassist.NotFoundException;

@Controller
@RequestMapping("/documentosFiscalesClient")
public class DocumentosNacionalesFunctionalityController {

	@Autowired
	@Qualifier("DocumentosNacionalesActions")
	private DocumentosNacionalesActions actioner; 
	
	@Autowired
	private ComprobanteStorageService storageService; 
	
	
	@PostMapping("/uploadFactura")
	public String recieveComprobanteFiscal(@RequestParam("files") MultipartFile[] files, @RequestParam String rfc, RedirectAttributes ra) {
		
		// initializing directories
		storageService.init();
		
		ArchivoPDF filePDF = null; 
		if (!files[0].isEmpty()) {
			
			ArchivoXML fileXML = (ArchivoXML) storageService.storePortalFile(files[0]);
			

		
			if (!files[1].isEmpty()) {
				
				filePDF = (ArchivoPDF) storageService.storePortalFile(files[1]);
				
			} 
	
				
			try {
				
				boolean value = actioner.upload(fileXML, filePDF);
				ra.addFlashAttribute("upload", value);
				
			} catch (NotFoundException e) {
				
				Log.activity(e.getMessage(), fileXML.getReceptor(), "ERROR_DB");
				ra.addFlashAttribute("upload", false);
			}
			
		} else {
						
			ra.addFlashAttribute("upload", false);	
		}
			
			return "redirect:/documentosFiscalesClient?rfc=" + rfc;
		
	}
	
	@GetMapping("/download/{method}/{repo}/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, repo, id, "d");
		
	}
	
	
	@GetMapping("/download/{method}/{repo}")
	public ResponseEntity<byte[]> download(@RequestParam List<Long> ids, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, repo, ids);
	
	}
	
	@GetMapping("/preview/{method}/{repo}/{id}")
	public ResponseEntity<Resource> preview(@PathVariable Long id, @PathVariable String method, @PathVariable String repo) {
		
		return actioner.download(method, repo, id, "p");
		
	}
	
	@GetMapping("/csv")
	public void downloadCsv(@RequestParam List<Long> ids, HttpServletResponse response) {
		
		actioner.downloadCsv(ids, response);
		
	}
	
	@GetMapping("/excel")
	public ResponseEntity<Resource> downloadExcel(@RequestParam List<Long> ids) {
		
		return actioner.downloadXls(ids);
		
	}
	

	@GetMapping(value = "/deleteMultipleFacturas")
	public String deleteMultipleComprobanteFiscal(@RequestParam List<Long> ids, @RequestParam String rfc) {
		
		actioner.deleteAll(ids);
		
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
			
			
	}
	
	@GetMapping(value = "/delete/{id}")
	public String deleteComprobanteFiscal(@PathVariable Long id, @RequestParam String rfc,
			RedirectAttributes ra) {
		
			
			ra.addFlashAttribute("delete", actioner.delete(id));
			
	
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
			
			
	}
	
	@PostMapping(value = "/update")
	public String update(ComprobanteFiscalDTO documento,  @RequestParam String rfc, @RequestParam("pdf") MultipartFile pdf) {
		
		
		if (!pdf.isEmpty()) {
			
			actioner.updateCfdFile(pdf, documento.getIdComprobanteFiscal());
			
		}
		
		// update object information normally 
		actioner.updateCfdInformation(documento);
			
	
		return "redirect:/documentosFiscalesClient?rfc=" + rfc;
	}
	
	@PostMapping(value = "/getVigencia/{id}")
	@ResponseBody
	public String getVigencia(@PathVariable Long id) {
		
		return actioner.refreshEstatusSat(id);
		
	}
	
	@PostMapping(value = "/supplier/update")
	public String updateSupplier(ProveedorDTO proveedor, @RequestParam("selectedCompany") String rfc, RedirectAttributes redirectAttributes) {
		try {
			
			actioner.updateSupplier(proveedor);
		
		} catch (EntityNotFoundException e) {
			Log.falla("Error al actualizar la información: " + e.getMessage(), "ERROR_DB");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} catch (UnknownError e) {
			Log.falla("Error al actualizar la información: " + e.getMessage(), "ERROR");
			redirectAttributes.addFlashAttribute("error", e.getMessage());
		} 
		
		return "redirect:/documentosFiscalesClient/catalogo?selectedCompany=" + rfc;
	}
	
	@GetMapping(value = "/supplier/delete/{id}")
	public String deleteSupplier(@PathVariable Long id, @RequestParam("selectedCompany") String rfc, RedirectAttributes ra) {
		try {
			
			actioner.deleteSupplier(id);
				
		} catch (NotFoundException e) {
			ra.addFlashAttribute("supplierNotFound", e.getMessage());
		}
		
		return "redirect:/documentosFiscalesClient/catalogo?selectedCompany=" + rfc;
	}
	
	@GetMapping(value = "/addUserSupplier")
	public String addUserSupplier(Model model) {
		
		model.addAttribute("user", new UserDTO());
		
		return "nueva-cuenta-proveedor-admin";
	}
	
	@PostMapping(value = "/addUserSupplier")
	public String saveAndRegisterUserSupplier(@ModelAttribute("user") UserDTO user, RedirectAttributes ra) {
		
		try {
			
			actioner.saveNewUserSupplier(user);			
			
		} catch (EntityExistsException | NotFoundException e) {
			Log.falla("Error al registrar el usuario proveedor: " + e.getMessage(), "ERROR_DB");
			ra.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:/registerSupplier";
		} 
		
		ra.addFlashAttribute("registerSuccess", true);
		return "redirect:/admin/users";
	}
	
	


	
}
