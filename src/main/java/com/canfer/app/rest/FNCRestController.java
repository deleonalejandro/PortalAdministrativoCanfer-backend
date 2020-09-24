package com.canfer.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.service.ComprobanteFiscalService;

@RestController
@RequestMapping(value = "/documentosFiscalesApi")
public class FNCRestController {
	
	@Autowired
	private ComprobanteFiscalService comprobanteFiscalService;
	
	@GetMapping("/facturas")
	public List<ComprobanteFiscal> getAll() {
	    return comprobanteFiscalService.findAll();
	  }
	  
	@GetMapping("/factura/{id}")
	public ComprobanteFiscal getOne(String uuid) {
		return comprobanteFiscalService.findByUUID(uuid);
	  }
	
	@GetMapping("/delete/{id}")
	public void deleteOneFactura(@PathVariable String uuid) {
		comprobanteFiscalService.delete(uuid);
	  }
	
	
}