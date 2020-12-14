package com.canfer.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String redirectUrl;
		
		String authentication = request.getParameter("auth");
		
		redirectUrl = "/login-error?auth=" + authentication;
		
		response.sendRedirect(redirectUrl);


	}

}
