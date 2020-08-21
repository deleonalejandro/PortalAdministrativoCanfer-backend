package com.canfer.app;

import javax.annotation.Resource;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.canfer.app.storage.FacturaStorageService;



@SpringBootApplication
public class PortalAdministrativoCanferApplication implements CommandLineRunner{

	@Resource
	FacturaStorageService facturaStorageService;

		
	public static void main(String[] args) {
		SpringApplication.run(PortalAdministrativoCanferApplication.class, args);
	
	
	}

	@Override
	public void run(String... args) throws Exception {
		facturaStorageService.init();
	}
	
	

}


