package com.canfer.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class getInvoice {

	@GetMapping(value="/contaduria-nacional")
    public String invoice(){
        return "contaduria-nacional";
    }
	
}
