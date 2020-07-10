package com.canfer.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("login")
	public String getLoginPage() {
		
		System.out.println("Pagina Login cargada correctamente");
		
		return "auth-login-basic.html"; //Navigate to login page
	}

}
