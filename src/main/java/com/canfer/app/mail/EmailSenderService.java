package com.canfer.app.mail;


import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 
 * Servicio para redactar y enviar mails
 * 
 * @author Alejandro de Leon y Yasmin Femerling
 * @date 09/08/2020
 */

@Service
public class EmailSenderService {
	
	// ==============
	// PRIVATE FIELDS
	// ==============
	
	@Autowired
    private JavaMailSender javaMailSender;
	

	public EmailSenderService(JavaMailSender javaMailSender) throws Exception {
        this.javaMailSender = javaMailSender;
    }

	
	// ==============
	// PUBLIC METHODS
	// ==============
	
	
	/**
	   * Metodo para escribir un mail y enviarlo
	   * 
	   * @return void
	   */
	
	public void sendEmail() {
		
		//Crete message
        SimpleMailMessage msg = new SimpleMailMessage();
        
        //Include attachment, subject, destination address and content
        msg.setTo("xialeexix@gmail.com");
        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        //Send mail
        javaMailSender.send(msg);
	

    }
	
	public void sendEmail(String to,String subject,String msgBody, String path){

	    MimeMessage message = javaMailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(msgBody);
	        helper.addAttachment("AvisoDePago.pdf", new File(path));
	        javaMailSender.send(message);
	    } catch (MessagingException e) {

	        e.printStackTrace();
	    }
	}

	
}
