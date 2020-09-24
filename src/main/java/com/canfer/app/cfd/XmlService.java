package com.canfer.app.cfd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class XmlService {

	public XmlService() {
		// Constructor
	}

	/* Transform the XML to a POJO */

	// Transform with a Multipart File
	public Comprobante xmlToObject(MultipartFile file) {
		JAXBContext context;
		BOMInputStream bis;
		try (InputStream in = file.getInputStream()) {
			bis = new BOMInputStream(in);
			context = JAXBContext.newInstance(Comprobante.class);
			return (Comprobante) context.createUnmarshaller()
					.unmarshal(new InputStreamReader(new BufferedInputStream(bis)));
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Transform with a Path
	public Comprobante xmlToObject(Path path) throws JAXBException, FileNotFoundException, IOException {
		// Make a file from the given path
		File file = path.toAbsolutePath().toFile();
		JAXBContext context;
		BOMInputStream bis;

		try (InputStream in = new FileInputStream(file)) {
			bis = new BOMInputStream(in);
			context = JAXBContext.newInstance(Comprobante.class);
			return (Comprobante) context.createUnmarshaller()
					.unmarshal(new InputStreamReader(new BufferedInputStream(bis)));
		} 

	}

	/* Transform the XML to a String type */

	// Transform with a Multipart File
	public String docToString(MultipartFile file) {
		try {
			InputStream is = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = br.readLine();
			}

			String xmlString = sb.toString();
			br.close();

			return xmlString;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Transform with a path
	public String docToString(Path path) {
		// Make a file from the given path
		File file = path.toAbsolutePath().toFile();

		try (InputStream is = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = br.readLine();
			}

			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
