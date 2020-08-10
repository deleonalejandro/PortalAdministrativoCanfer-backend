package com.canfer.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class TestController {

	@GetMapping(value="/")
    public String test(){
        return "contaduria-nacional";
    }
	
}
