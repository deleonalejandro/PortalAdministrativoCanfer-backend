package com.canfer.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class getIndex {

	@GetMapping(value="/")
    public String invoice(){
        return "invoice";
    }
	
}
