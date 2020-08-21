package com.canfer.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.ResourceAccessException;


import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.service.CargaDocumentoService;

@Controller
public class FilesController {
	
	@Autowired
	CargaDocumentoService cargaDocumentoService;
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	ConsecutivoRepository consecutivoRepository;
	
	public FilesController() {
		// Constructor vacio
	}
	
	@GetMapping(name = "/cargar")
	public String uploadDoc() {
			
		//List<InputStream> files = new ArrayList<>();
		File testXML = new File("C:\\Users\\alex2\\eclipse-workspace\\test.xml");
		File testPDF = new File("C:\\\\Users\\\\alex2\\\\eclipse-workspace\\\\test.pdf");
		FacturaNotaComplemento factura = null;
		
		
		try (InputStream inXML = new FileInputStream(testXML)) {
			factura = cargaDocumentoService.saveXML(inXML, "Documentos Fiscales");
			
			InputStream inPDF = new FileInputStream(testPDF);
			
			cargaDocumentoService.savePDF(inPDF, factura);
			
		} catch (FileNotFoundException | FileAlreadyExistsException  | ResourceAccessException e) {
			e.printStackTrace();
			return "index";
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return "index";
	}

}
