package com.canfer.app.webService;


import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;



import com.canfer.app.wsdl.ValidayVerificaXMLResponse;
import com.canfer.app.wsdl.ValidayVerificaXML;

@Service
public class Client extends WebServiceGatewaySupport {

		
	  public ValidayVerificaXMLResponse getInfo(String user, String passwd, String pxml) {
		  
	    ValidayVerificaXML request = new ValidayVerificaXML();
	    
	    request.setPcontraseña(passwd);
	    request.setPusuario(user);
	    request.setPxml(pxml);


	    ValidayVerificaXMLResponse response = (ValidayVerificaXMLResponse) getWebServiceTemplate()
	        .marshalSendAndReceive("https://invoiceone.mx/ValidaFiscal/wsValidador.asmx", request, new SoapActionCallback(
	        		"http://tempuri.org/ValidayVerificaXML"));

	    return response;
	  }

	}