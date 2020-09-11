package com.canfer.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	
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

}
