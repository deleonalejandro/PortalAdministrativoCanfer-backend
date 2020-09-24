package com.canfer.app.webservice.invoiceone;


import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.canfer.app.wsdl.invoiceone.ObtenerEstatusCuenta;
import com.canfer.app.wsdl.invoiceone.ObtenerEstatusCuentaResponse;
import com.canfer.app.wsdl.invoiceone.ValidayVerificaXML;
import com.canfer.app.wsdl.invoiceone.ValidayVerificaXMLResponse;

@Service
public class Client extends WebServiceGatewaySupport {

		
	  public ValidayVerificaXMLResponse getInfo(String user, String passwd, String pxml) {
		  
	    ValidayVerificaXML request = new ValidayVerificaXML();
	    
	    request.setPcontraseña(passwd);
	    request.setPusuario(user);
	    request.setPxml(pxml);


	    return (ValidayVerificaXMLResponse) getWebServiceTemplate()
	        .marshalSendAndReceive("https://invoiceone.mx/ValidaFiscal/wsValidador.asmx", request, new SoapActionCallback(
	        		"http://tempuri.org/ValidayVerificaXML"));

	  }
	  
	  public ObtenerEstatusCuentaResponse getInfo(String user, String passwd) {
		  
			//Create the request from the information
		  ObtenerEstatusCuenta request = new ObtenerEstatusCuenta();
		   
		  //Fill request
		  request.setPContraseña(passwd);
		  request.setPUsuario(user);
		  //Make a response through marshaller
		  return (ObtenerEstatusCuentaResponse) getWebServiceTemplate()
		    .marshalSendAndReceive("https://invoiceone.mx/ValidaFiscal/wsValidador.asmx", request, new SoapActionCallback(
		    		"http://tempuri.org/ObtenerEstatusCuenta"));
		 }
	  
	  

	}