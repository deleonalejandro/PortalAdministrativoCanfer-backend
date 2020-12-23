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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.canfer.app.dto.RutaAlmacenamientoDTO;
import com.canfer.app.mail.EmailThread;
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
		model.addAllAttributes(rutas);
		
		
		return "admin-panel";
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
			
			// update storage services
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
					return false;
					//TODO agregar al log los errores 
				}
				
			}
				
			
		} catch (InvalidPathException e) {
			return false;
			//TODO agregar al log los errores
		}
		
	}

}
