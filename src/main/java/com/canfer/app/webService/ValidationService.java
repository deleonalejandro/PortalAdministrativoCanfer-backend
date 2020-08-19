package com.canfer.app.webService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import com.canfer.app.wsdl.ValidayVerificaXMLResponse;
import com.sun.xml.messaging.saaj.util.Base64;

@Service
public class ValidationService {
	
	@Autowired 
	private ClientConfiguration clientConfiguration;
	@Autowired
	private XMLtoString xmlToString;
	@Autowired
	private Client client;
	@Autowired
	private ResponseWebService validationAnswer; 


	public List<String> validaVerifica(String path){
		
		
		try {
		Jaxb2Marshaller marshaller = clientConfiguration.marshaller();
		client = clientConfiguration.Client(marshaller);
		
		//create string from xml doc
		String xmlString = xmlToString.docToString(path);
		
		//use user and passwd from account of web service
		String user = "Pruebas"; 
	    String passwd = "Htp.7894"; 
	    
	    //send request to web service
        ValidayVerificaXMLResponse response = client.getInfo(user,passwd,xmlString);
        
        //decode response
        String decodedResponse = Base64.base64Decode(response.getValidayVerificaXMLResult());
        
        //get answer from validation in list
        List<String> validation = validationAnswer.getValidation(decodedResponse);
        
        return validation; 
        
		}catch(Exception e) {
			e.printStackTrace();
			return null; 

		}
		
		
	}
	
	
}
