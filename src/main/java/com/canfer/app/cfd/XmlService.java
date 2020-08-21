package com.canfer.app.cfd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;

@Service
public class XmlService {
	
	public XmlService() {
		//Constructor vacio
	}
	
	public Comprobante xmlToObject(File file) throws JAXBException, IOException{
		JAXBContext context = JAXBContext.newInstance(Comprobante.class);
	    return (Comprobante) context.createUnmarshaller().unmarshal(new FileReader(file));
	}
	
	public String docToString(String filePath)
	{
	    try {
	    	File xmlFile = new File(filePath);
	        try(Reader fileReader = new FileReader(xmlFile)) {
	        	
	        	BufferedReader bufReader = new BufferedReader(fileReader); 
	        	StringBuilder sb = new StringBuilder(); 
	        	String line = bufReader.readLine(); 
	        	while( line != null){ 
	        		sb.append(line).append("\n"); 
	        		line = bufReader.readLine(); 
	        	}
	        	
	        	String xmlString = sb.toString(); 
	        	bufReader.close();
	        	return xmlString;
	        }      
	    
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        return null; 
	    }
	}
	

}
