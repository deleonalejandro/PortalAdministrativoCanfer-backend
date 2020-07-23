package com.canfer.app;


import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;



import com.canfer.app.wsdl.ValidayVerificaXMLResponse;
import com.canfer.app.wsdl.ValidayVerificaXML;

public class Client extends WebServiceGatewaySupport {

		
	  public ValidayVerificaXMLResponse getInfo(String user, String passwd, String pxml) {
		  
	    ValidayVerificaXML request = new ValidayVerificaXML();
	    
	    request.setPcontraseña(passwd);
	    request.setPusuario(user);
	    request.setPxml(pxml);


	    ValidayVerificaXMLResponse response = (ValidayVerificaXMLResponse) getWebServiceTemplate()
	        .marshalSendAndReceive("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc", request, new SoapActionCallback(
	                "http://tempuri.org/IConsultaCFDIService/Consulta"));

	    return response;
	  }

	}