package com.canfer.app.mail;


import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.annotation.Validated;

@Configuration
@PropertySource("classpath:mail.properties")
@Validated
public class EmailSenderProperties extends JavaMailSenderImpl {
	
	public EmailSenderProperties(@Value("${senderhost}") String host, @Value("${senderusername}") String username,
    		@Value("${senderpassword}") String password, @Value("${senderport}") int port) {
		
		super();
		this.setHost(host);
		this.setUsername(username);
		this.setPassword(password);
		this.setPort(port);
		Properties props = this.getJavaMailProperties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.starttls.required", "true");
	    props.put("mail.debug", "false");
			
	}
	
	public void changeUsername(String username) {
		this.setUsername(username);
	}
	
	public void changePassword(String password) {
		this.setPassword(password);
	}
	
	public void changePort(int port) {
		this.setPort(port);
	}
	
	public void changeHost(String host) {
		this.setHost(host);
	}
	
	
}
