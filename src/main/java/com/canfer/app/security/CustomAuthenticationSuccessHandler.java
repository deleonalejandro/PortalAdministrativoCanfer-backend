package com.canfer.app.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {


		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		
		if (roles.contains("ROLE_USER_PROVEEDOR")) {		
			response.sendRedirect("/dashboardSupplier"); 
			
		} else if (roles.contains("ROLE_USER_CAJA")) {
			response.sendRedirect("/cajachicaclient/dashboard");
			
		} else if (roles.contains("ROLE_ADMIN")){
			response.sendRedirect("/admin/cpanel");

		} else {
			response.sendRedirect("/login");
		}
		
	}

}
