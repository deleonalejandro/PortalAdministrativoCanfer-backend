package com.canfer.app.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.canfer.app.async.CrystalReportService;

@Controller
public class MainController {
	
	@Autowired
    CrystalReportService crService;
	
	public MainController() {
		// Constructor
	}
	
	@GetMapping(value = "/")
	public String home() {
		return "index";
	}
	
	@GetMapping(value = "/documentosFiscalesClient")
	public String getModuloDocumentosFiscales() {
		return "documentos-fiscales";
	}
	
	@GetMapping(value = "/crystal")
	public String crystal() throws Exception {
		crService.exportPDF();
         
		return "documentos-fiscales";
	}

}
