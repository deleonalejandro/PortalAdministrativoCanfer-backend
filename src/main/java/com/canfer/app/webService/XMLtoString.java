package com.canfer.app.webService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.springframework.stereotype.Service;

@Service
public class XMLtoString {
	 
	public String docToString(String filePath)
	{
	    try {
	    	File xmlFile = new File(filePath);
	        Reader fileReader = new FileReader(xmlFile); 
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
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	        return null; 
	    }
	}
	
	    
	}
	



