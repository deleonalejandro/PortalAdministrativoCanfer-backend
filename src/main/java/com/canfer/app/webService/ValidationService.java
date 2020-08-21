package com.canfer.app.webService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import com.canfer.app.cfd.XmlService;
import com.canfer.app.wsdl.ValidayVerificaXMLResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ValidationService {
	
	@Autowired 
	private ClientConfiguration clientConfiguration;
	@Autowired
	private XmlService xmlService;
	@Autowired
	private Client client;
	@Autowired
	private ResponseWebService validationAnswer; 


	public List<String> validaVerifica(String path){
		
		try {
		Jaxb2Marshaller marshaller = clientConfiguration.marshaller();
		client = clientConfiguration.Client(marshaller);
		
		//create string from xml doc
		String xmlString = xmlService.docToString(path);
		
		//use user and passwd from account of web service
		String user = "Pruebas"; 
	    String passwd = "Htp.7894"; 
	    
	    //send request to web service
        ValidayVerificaXMLResponse response = client.getInfo(user,passwd,xmlString);
        
        //decode response
        byte[] decodedResponse = Base64.getDecoder().decode(response.getValidayVerificaXMLResult());
        String utf8EncodedString = new String(decodedResponse, StandardCharsets.UTF_8);
        
        //get answer from validation in list
        return validationAnswer.getValidation(utf8EncodedString);
        
		}catch(Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
		
		
	}
	
	
}
