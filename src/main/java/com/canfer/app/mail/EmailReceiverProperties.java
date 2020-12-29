package com.canfer.app.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;


@Configuration
@PropertySource("classpath:mail.properties")
@ConfigurationProperties
@Validated
public class EmailReceiverProperties {
	// server properties
    private String hostname;
    private String port;
    private String receiveProtocol;
    
    // account properties
    private String email;
    private String password;
    
    
    
	public String getHostname() {
		
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getReceiveProtocol() {
		return receiveProtocol;
	}
	public void setReceiveProtocol(String receiveProtocol) {
		this.receiveProtocol = receiveProtocol;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    


}
