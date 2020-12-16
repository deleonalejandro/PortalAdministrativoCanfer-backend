package com.canfer.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.canfer.app.mail.EmailThread;
import com.canfer.app.pdfExport.DBThread;

@Controller
@RequestMapping("/admin")
public class ControlPanelController {
	
	@Autowired
	private EmailThread emailThread;
	
	@Autowired
	private DBThread dbThread;
	
	@GetMapping(value = "/cpanel")
	public String getCPanel(Model model) {
		
		model.addAttribute("emailStatus", emailThread.getStatus());
		model.addAttribute("syncPaymentStatus", dbThread.getStatus());
		
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
	
	

}
