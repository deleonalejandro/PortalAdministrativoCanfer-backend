package com.canfer.app.webService;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Service
public class ResponseWebService {
	
	public List<String> getValidation(String response)
	{
		
		List<String> answer = new ArrayList<String>();

        
        DocumentBuilder db;
		try {
			
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
			 InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(response));

	        Document doc = db.parse(is);

			Element root = doc.getDocumentElement();
			
			String[] valuesInQuotes2 = StringUtils.substringsBetween(root.getAttributes().item(2).toString() , "\"", "\"");
			
			if( valuesInQuotes2[0].equalsIgnoreCase("ACCEPTED")) {
				answer.add("true");
				answer.add("El documento es válido ante el SAT.");
			}else {
				answer.add("false");
				String message = doc.getDocumentElement().getTextContent();
		        
		        String[] valuesInQuotes1 = StringUtils.substringsBetween(message, "InvoiceOne", "2ESTATUS");
				
				answer.add(valuesInQuotes1[0]);
			}
			 
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
		
		
		return answer;
		
	}
}
