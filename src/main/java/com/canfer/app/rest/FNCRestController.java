package com.canfer.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.service.FacturaNotaComplementoService;

@RestController
@RequestMapping(value = "/documentosFiscalesApi")
public class FNCRestController {
	
	@Autowired
	private FacturaNotaComplementoService facturaNotaComplementoService;
	
	@GetMapping("/facturas")
	public List<FacturaNotaComplemento> getAll() {
	    return facturaNotaComplementoService.findAll();
	  }
	  
	@GetMapping("/factura/{id}")
	public FacturaNotaComplemento getOne(String uuid) {
		return facturaNotaComplementoService.findByUUID(uuid);
	  }
	
	@GetMapping("/delete/{id}")
	public void deleteOneFactura(@PathVariable String uuid) {
		facturaNotaComplementoService.delete(uuid);
	  }
	
	
}