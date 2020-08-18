package com.canfer.app.cfd;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;

@Service
public class CfdXmlService {
	
	public CfdXmlService() {
		//Constructor vacio
	}
	
	public Comprobante xmlToObject(File file) throws JAXBException, IOException{
		JAXBContext context = JAXBContext.newInstance(Comprobante.class);
	    return (Comprobante) context.createUnmarshaller().unmarshal(new FileReader(file));
	}

}
