package com.canfer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.canfer.app.model.Log;

@SpringBootApplication
@EnableConfigurationProperties
public class PortalAdministrativoCanferApplication {
		
	public static void main(String[] args) {
		
		SpringApplication.run(PortalAdministrativoCanferApplication.class, args);
		Log.falla("La aplicación se ha inicializado exitosamente.", "RUN");
	
	}
	
	
}


