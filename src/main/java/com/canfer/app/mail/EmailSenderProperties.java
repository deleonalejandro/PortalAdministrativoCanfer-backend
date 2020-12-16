package com.canfer.app.mail;


import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderProperties extends JavaMailSenderImpl {
	
	public EmailSenderProperties() {
		super();
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
