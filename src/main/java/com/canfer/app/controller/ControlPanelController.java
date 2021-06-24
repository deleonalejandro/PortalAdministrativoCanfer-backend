package com.canfer.app.controller;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.RutaAlmacenamientoDTO;
import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.mail.EmailThread;
import com.canfer.app.model.Log;
import com.canfer.app.model.RutaAlmacenamiento;
import com.canfer.app.pdfExport.DBThread;
import com.canfer.app.repository.RutaAlmacenamientoRepository;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.LogoStorageService;
import com.canfer.app.storage.StorageProperties;

@Controller
@RequestMapping("/admin")
public class ControlPanelController {
	
	@Autowired
	private EmailThread emailThread;
	
	@Autowired
	private RutaAlmacenamientoRepository rutasRepo;
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Autowired
	private DBThread dbThread;
	
	@Autowired
	private ComprobanteStorageService comprobanteStorage;
	
	@Autowired
	private LogoStorageService logoStorage;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@GetMapping(value = "/cpanel")
	public String getCPanel(Model model) {
		
		/* storage paths map */
		HashMap<String, String> rutas = new HashMap<>();

		rutas.put("facturas", storageProperties.getFacturasLocation().toString());
		rutas.put("entryPortal", storageProperties.getEntryPortalLocation().toString());
		rutas.put("entries", storageProperties.getEntriesLocation().toString());
		rutas.put("error", storageProperties.getErrorLocation().toString());
		rutas.put("logo", storageProperties.getLogoLocation().toString());
		rutas.put("log", storageProperties.getLogLocation().toString());
		
		/* adding attributes to the model */
		model.addAttribute("emailStatus", emailThread.getStatus());
		model.addAttribute("syncPaymentStatus", dbThread.getStatus());
		model.addAttribute("newDocStatus", emailSenderService.isBooleanEmailNewDoc());
		model.addAttribute("avisoPagoStatus", emailSenderService.isBooleanEmailAvisoPago());
		model.addAttribute("updateDocStatus", emailSenderService.isBooleanEmailUpdateDoc());
		model.addAttribute("newAccountStatus", emailSenderService.isBooleanEmailNewAccount());
		model.addAllAttributes(rutas);
		
		
		return "admin-panel";
	}
	
	@GetMapping(value = "/clasificaciones/cc")
	public String getClasificaciones(Model model) {
		
		return "clasificaciones-caja-chica";
	}
	
	@GetMapping("/stopEmailThread")
	public String stopEmailThread() {
		
		emailThread.stopCheckingMail();
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/activateEmailThread")
	public String activateEmailThread() {
		
		emailThread.runCheckingMail();
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/stopSyncPayments")
	public String stopSyncPayments() {
		
		dbThread.stopSyncPayments();
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/activateSyncPayments")
	public String activateSyncPayments() {
		
		dbThread.runSyncPayments();
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/notificationavisopago")
	public String notiAvisoPago(@RequestParam String bit) {
		
		if (bit.equalsIgnoreCase("on")) {
			
			emailSenderService.startEmailAvisoPago();
			
		} else if (bit.equalsIgnoreCase("off")) {
			
			emailSenderService.stopEmailAvisoPago();
			
		}
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/notificationnewdoc")
	public String notiNewDoc(@RequestParam String bit) {
		
		if (bit.equalsIgnoreCase("on")) {
			
			emailSenderService.startEmailNewDoc();
			
		} else if (bit.equalsIgnoreCase("off")) {
			
			emailSenderService.stopEmailNewDoc();
			
		}
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/notificationupdatedoc")
	public String notiUpdateDoc(@RequestParam String bit) {
		
		if (bit.equalsIgnoreCase("on")) {
			
			emailSenderService.startEmailUpdateDoc();
			
		} else if (bit.equalsIgnoreCase("off")) {
			
			emailSenderService.stopEmailUpdateDoc();
			
		}
		
		return "redirect:/admin/cpanel";
		
	}
	
	@GetMapping("/notificationnewaccount")
	public String notiNewAccount(@RequestParam String bit) {
		
		if (bit.equalsIgnoreCase("on")) {
			
			emailSenderService.startEmailNewAccount();
			
		} else if (bit.equalsIgnoreCase("off")) {
			
			emailSenderService.stopEmailNewAccount();
			
		}
		
		return "redirect:/admin/cpanel";
	}
	
	
	//TODO consider that more storage paths are likely to be added into the system. Consider making one method for each storage path.
	@PostMapping("/updatePath")
	public String updateStoragePath(RutaAlmacenamientoDTO newPath, RedirectAttributes ra) {
		
		RutaAlmacenamiento updatePath;
		
		// check both paths are equal; check if path is valid
		if (newPath.getRuta().equals(newPath.getCheckRuta()) && isPathValid(newPath.getRuta())) {
			
			// update individual path
			updatePath = rutasRepo.findByDescripcion(newPath.getDescripcion());
			updatePath.setRuta(newPath.getRuta());
			
			rutasRepo.saveAndFlush(updatePath);
			
			// TODO PROBLEM HERE; update storage services
			logoStorage.updatePaths();
			comprobanteStorage.updatePaths();
			
			ra.addFlashAttribute("success", true);
			
			return "redirect:/admin/cpanel";
				
				
		}
		
		
		ra.addFlashAttribute("success", false);
		
		return "redirect:/admin/cpanel";
	
	}
	
	
	private boolean isPathValid(String path) {
		
		try {
			
			Path dir = Paths.get(path);
			
			if (dir.toFile().isDirectory()) {
				
				return true;
				
				
			} else {
				
				try {
					
					if (dir.toFile().mkdirs()) {
						
						return true;
						
					} else {
						
						return false;
					}
					
				} catch (SecurityException e) {
					Log.falla("Ocurrió un problema al intentar crear los directorios de la ruta.", "ERROR_STORAGE");
					return false;
				 
				}
				
			}
				
			
		} catch (InvalidPathException e) {
			Log.falla("La ruta especificada no es válida.", "ERROR_STORAGE");
			return false;
		}
		
	}

}
