package com.canfer.app.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.canfer.app.log.Log;
import com.canfer.app.mail.sender.EmailSenderService;

@Controller
public class SendEmailController {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping(value = "/sendmail")
    public String sendmail() {

    	try {
        emailSenderService.sendEmail();
    	} catch(Exception e){
    		Log.falla("No se pudo conectar al servidor para mandar Emails");
    		Log.warn(e.getMessage());
    	}
    	
        return "index";
    }
}