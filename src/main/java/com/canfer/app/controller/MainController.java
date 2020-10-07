package com.canfer.app.controller;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.canfer.app.async.CrystalReports;

import org.apache.commons.io.FileUtils;
import java.io.File;

@Controller
public class MainController {
	
	@Autowired
    CrystalReports crService;
	
	public MainController() {
		// Constructor
	}
	
	@GetMapping(value = "/")
	public String home() {
		return "index";
	}
	
	@GetMapping(value = "/documentosFiscalesClient")
	public String getModuloDocumentosFiscales() {
		return "documentos-fiscales";
	}
	
	@GetMapping(value = "/crystal")
	public String crystal() throws Exception {
		
		 String fileName = "product.pdf";
		 String author = "autor";
		 InputStream reportInputStream = crService.exportPdf(author);
         FileUtils.copyInputStreamToFile(reportInputStream, new File("report" + File.separator + fileName));
         
		return "documentos-fiscales";
	}

}
