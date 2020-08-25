package com.canfer.app.webservice.invoiceone;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.canfer.app.cfd.XmlService;
import com.canfer.app.wsdl.invoiceone.ObtenerEstatusCuentaResponse;
import com.canfer.app.wsdl.invoiceone.ValidayVerificaXMLResponse;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

	public List<String> validaVerifica(MultipartFile file) {

		try {
			Jaxb2Marshaller marshaller = clientConfiguration.marshaller();
			client = clientConfiguration.Client(marshaller);

			// create string from xml doc
			String xmlString = xmlService.docToString(file);

			// use user and passwd from account of web service
			String user = "Pruebas";
			String passwd = "Htp.7894";

			// send request to web service
			ValidayVerificaXMLResponse response = client.getInfo(user, passwd, xmlString);
			// decode response
			byte[] decodedResponse = Base64.getDecoder().decode(response.getValidayVerificaXMLResult());
			String utf8EncodedString = new String(decodedResponse, StandardCharsets.UTF_8);
			// get answer from validation in list
			return validationAnswer.getValidation(utf8EncodedString);
			
		} catch (SoapFaultClientException e) {
			e.printStackTrace();
			return Collections.emptyList();          
		}

	}

	public void estatusCuenta() {
		try {
			Jaxb2Marshaller marshaller = clientConfiguration.marshaller();
			client = clientConfiguration.Client(marshaller);

			// use user and passwd from account of web service
			String user = "PAE92070";
			String passwd = "a0e6$X8x";

			// send request to web service
			ObtenerEstatusCuentaResponse response = client.getInfo(user, passwd);

			// get list of results
			List<Integer> estatus = new ArrayList<>();

			estatus.add(response.getObtenerEstatusCuentaResult().getPruebas().getFoliosDisponibles());
			estatus.add(response.getObtenerEstatusCuentaResult().getPruebas().getFoliosUtilizados());
			estatus.add(response.getObtenerEstatusCuentaResult().getProduccion().getFoliosDisponibles());
			estatus.add(response.getObtenerEstatusCuentaResult().getProduccion().getFoliosUtilizados());

		} catch (SoapFaultClientException e) {
			e.printStackTrace();
		}
	}

}
