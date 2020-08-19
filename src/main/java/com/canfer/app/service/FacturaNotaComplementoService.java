package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.FacturaNotaComplementoRepository;
import com.canfer.app.webService.Client;
import com.canfer.app.webService.ClientConfiguration;
import com.canfer.app.webService.ResponseWebService;
import com.canfer.app.webService.XMLtoString;
import com.canfer.app.wsdl.ValidayVerificaXMLResponse;
import com.sun.xml.messaging.saaj.util.Base64;

@Service
public class FacturaNotaComplementoService {
	
	@Autowired
	FacturaNotaComplementoRepository facturaNotaComplementoRepository;
	private final String documentNotFound = "El documento no existe.";
	
	public List<FacturaNotaComplemento> findAll(){
		
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		
		return facturaNotaComplementoRepository.findAll();		
	}
	
	public FacturaNotaComplemento findById(Long id) {
		Optional<FacturaNotaComplemento> fncDocumento = facturaNotaComplementoRepository.findById(id);
		//Check if the document really exists in the database.
		if (fncDocumento.isEmpty()) {
			throw new DataAccessResourceFailureException(documentNotFound);
		}
		
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		//Logica para rechazar solicitudes no relacionadas al usuario principal.
		
		return fncDocumento.get();
		
	}
	
	public FacturaNotaComplemento findByUUID(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(documentNotFound);
		}
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		//Logica para rechazar solicitudes no relacionadas al usuario principal.
		
		return fncDocumento;
	}
	
	public void delete(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(documentNotFound);
		}
		
		facturaNotaComplementoRepository.delete(fncDocumento);
	}
	
	public FacturaNotaComplemento save() {
		//Business logic
		//Check for the user principal
		/*--------------------------------*/
		/*   Web service validation        */
		return null;
	}
	
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
