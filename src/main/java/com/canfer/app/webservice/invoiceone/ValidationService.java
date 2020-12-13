package com.canfer.app.webservice.invoiceone;

import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.internal.util.xml.XmlInfrastructureException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.Log;
import com.canfer.app.wsdl.invoiceone.ObtenerEstatusCuentaResponse;
import com.canfer.app.wsdl.invoiceone.ValidayVerificaXMLResponse;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class ValidationService {
	
	private Jaxb2Marshaller marshaller;
	private Client client;

	// using the client configuration
	public ValidationService(ClientConfiguration clientConfiguration) {
		this.marshaller = clientConfiguration.marshaller();
		this.client = clientConfiguration.Client(this.marshaller);
		
	}
	
	/*
	 * INVOICE ONE WEBSERVICE METHODS
	 *  
	 **/
	
	//Valida y Verifica con MultipartFile 
	public List<String> validaVerifica(ArchivoXML file) {

		try {
			
			// create string from xml doc
			String xmlString = file.toString();

			//TODO REMOVE HARDCODEO
			// use user and passwd from account of web service
			String user = "Pruebas";
			String passwd = "Htp.7894";

			// send request to web service
			ValidayVerificaXMLResponse response = client.getInfo(user, passwd, xmlString);
			// decode response
			byte[] decodedResponse = Base64.getDecoder().decode(response.getValidayVerificaXMLResult());
			String utf8EncodedString = new String(decodedResponse, StandardCharsets.UTF_8);
			// get answer from validation in list
			return processAnswer(utf8EncodedString);
			
		} catch (SoapFaultClientException e) {
			Log.falla("No se pudo conectar con el Web Service de INVOICE ONE.", "ERROR_CONNECTION");;
			return Collections.emptyList();          
		} catch (XmlInfrastructureException e) {
			Log.falla("Ocurrió un error con el XML: "+e.getMessage(), "ERROR_FILE");
			return Arrays.asList("0", "Este documento no fue procesado por el Web Service", "No encontrado");          
		}
		

	}
	
	

	public void estatusCuenta() {
		try {
			//TODO REMOVE HARDCODEO
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
	
	private List<String> processAnswer(String response) {
		
		List<String> answer = new ArrayList<>();
		DocumentBuilder db;
		try {

			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(response));
			Document doc = db.parse(is);
			Element root = doc.getDocumentElement();
			String message = doc.getDocumentElement().getTextContent();
			String[] valuesInQuotes2 = StringUtils.substringsBetween(root.getAttributes().item(2).toString(), "\"",
					"\"");
			if (valuesInQuotes2[0].equalsIgnoreCase("ACCEPTED")) {
				answer.add("true");
				answer.add("Este documento es válido ante el SAT.");
				answer.add(StringUtils.substringAfter(message, "2ESTATUS SAT:"));
			} else {
				answer.add("false");
				answer.add(StringUtils.substringBetween(message, "InvoiceOne", "2ESTATUS"));
				answer.add("No encontrado");
			}

		} catch (ParserConfigurationException e) {
			Log.falla("No se pudo leer el documento XML.", "ERROR_FILE");
			e.printStackTrace();
		} catch (SAXException e) {
			Log.falla("No se pudo leer el documento XML.", "ERROR_FILE");
			e.printStackTrace();
		} catch (IOException e) {
			Log.falla("No se pudo leer el documento XML.", "ERROR_FILE");
			e.printStackTrace();
		}

		return answer;
	}

}
