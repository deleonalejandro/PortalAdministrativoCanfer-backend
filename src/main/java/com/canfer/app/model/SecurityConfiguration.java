package com.canfer.app.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth
				.inMemoryAuthentication()
				.withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN").authorities("CAJACHICA")
				.and()
				.withUser("alex").password(passwordEncoder().encode("alex123")).roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http	
				.authorizeRequests()
				.antMatchers("/index.html").permitAll()
				.antMatchers("/users").authenticated()
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/cajachica/").hasAuthority("CAJACHICA")
				.and()
				.httpBasic();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
