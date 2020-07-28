package com.canfer.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.canfer.app.model.Estado;
import com.canfer.app.model.Municipio;
import com.canfer.app.repository.EstadoRepository;
import com.canfer.app.repository.MunicipioRepository;


@Controller
public class TestController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	
	public TestController() {
		// Constructor del controlador
	}

	
	@GetMapping(value = "/test")
	public String test() {
		Estado estate = new Estado("Coahuila");
		estadoRepository.save(estate);
		
		Municipio municipio = new Municipio("Saltillo");
		municipio.setEstado(estate);
		
		municipioRepository.save(municipio);
		
		//
		return "index";
	}
	
	@GetMapping(value = "/facturas")
	public String facturas() {
		//
		return "facturas";
	}
	
	

}
