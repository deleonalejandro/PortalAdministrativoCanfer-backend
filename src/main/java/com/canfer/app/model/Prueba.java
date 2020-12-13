package com.canfer.app.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.canfer.app.service.EmpresaService;

@Configurable
public class Prueba {

	@Autowired
	private EmpresaService empresaService;
	
	public Prueba() {
		// TODO Auto-generated constructor stub
	}
	
	public void print() {
		empresaService.printTest();
	}
}
