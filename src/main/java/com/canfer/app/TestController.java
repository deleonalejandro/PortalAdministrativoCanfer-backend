package com.canfer.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.canfer.app.webservice.sat.SatVerificacionService;


public class TestController {

	@Autowired
	SatVerificacionService s; 
	@GetMapping(value = "/pruebas")
	public void pruebas()  {
		

    }

}