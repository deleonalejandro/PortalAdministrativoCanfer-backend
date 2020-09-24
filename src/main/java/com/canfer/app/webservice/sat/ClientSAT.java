package com.canfer.app.webservice.sat;


import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXBElement;
import com.canfer.app.wsdl.sat.Consulta;
import com.canfer.app.wsdl.sat.ConsultaResponse;
import com.canfer.app.wsdl.sat.ObjectFactory;

@Service
public class ClientSAT extends WebServiceGatewaySupport {

		
	  public ConsultaResponse getInfo(String expresionImpresa) {
		  
		ObjectFactory convertidor = new ObjectFactory();
	    Consulta request = new Consulta();
	     JAXBElement<String> expresionImpresa2 = convertidor.createConsultaExpresionImpresa(expresionImpresa);
	    request.setExpresionImpresa(expresionImpresa2);


	    return (ConsultaResponse) getWebServiceTemplate()
		        .marshalSendAndReceive("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc", request, new SoapActionCallback(
		                "http://tempuri.org/IConsultaCFDIService/Consulta"));
	  }

}
