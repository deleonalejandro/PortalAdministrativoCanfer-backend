package com.canfer.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserPrincipalDetailsService userPrincipalDetailsService;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	public SecurityConfiguration(UserPrincipalDetailsService userPrincipalDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		this.userPrincipalDetailsService = userPrincipalDetailsService;
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
	}

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//We pass our authentication provider
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http	
				.formLogin()
				.loginPage("/login")
				.successHandler(customAuthenticationSuccessHandler)
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dashboard")
				.failureUrl("/login-error")
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccess")
				.and()
				.authorizeRequests()
				.antMatchers("/dashboard").authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
				
				.and()
				.httpBasic()
				.and()
				.csrf().disable();
		http
				.headers().frameOptions().disable();
	}
	
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		//Configuration of the provider
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);
		
		return daoAuthenticationProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
