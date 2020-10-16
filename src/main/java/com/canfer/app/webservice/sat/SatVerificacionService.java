package com.canfer.app.webservice.sat;


import org.apache.commons.lang.StringUtils;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Log;
import com.canfer.app.wsdl.sat.ConsultaResponse;

@Service
public class SatVerificacionService {

	private Jaxb2Marshaller marshaller;
	private ClientSAT client;

	// using the client configuration
	public SatVerificacionService(ClientConfigurationSAT clientConfiguration) {
		this.marshaller = clientConfiguration.marshallerSAT();
		this.client = clientConfiguration.ClientSAT(this.marshaller);
		
	}
		

	public String validaVerifica(String expresionImpresa){
		
		try {
		
	    //send request to web service
		ConsultaResponse response = client.getInfo(expresionImpresa);
        return response.getConsultaResult().getValue().getEstado().getValue();
        
		}catch(Exception e) {
			Log.general(e.getLocalizedMessage());
			return "Error al Procesar";
		}
		
		
	}
	
	
}