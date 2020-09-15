package com.canfer.app.cfd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.input.BOMInputStream;

public class TestClass {

	public static void main(String[] args) throws JAXBException, FileNotFoundException {
		JAXBContext context;
		Comprobante comprobante;
		File file = new File("C:\\Users\\alex2\\Desktop\\Canfer Workspace\\test-cases\\BCO130927JU4_119097.xml");
		InputStream in = new FileInputStream(file);
		try {
			BOMInputStream bomInputStream = new BOMInputStream(in);
			context = JAXBContext.newInstance(Comprobante.class);
			comprobante = (Comprobante) context.createUnmarshaller().unmarshal(new InputStreamReader(new BufferedInputStream(bomInputStream)));
			System.out.println(comprobante);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
