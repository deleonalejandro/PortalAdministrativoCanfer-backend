package com.canfer.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("newUser")
	public String getLoginPage() {
		
		System.out.println("Pagina Login cargada correctamente");
		
		return "crear-usuario"; //Navigate to user page
	}
	
	@RequestMapping("admin")
	public String getAdminPage() {
		return "account-security";
	}

	@RequestMapping("cajachica/")
	public String getCajaChica() {
		return "alerts";
	}
}
