package com.canfer.app.model;

import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

public class PortalProveedorActions extends DocumentosNacionalesActions {
	
	@GetMapping(value = "/csvProveedor")
	public void exportAllCSV(HttpServletResponse response, @RequestParam String rfc, @RequestParam String clave) throws Exception {
		 try {
			 
				//set file name and content type
			        String filename = "CFDIs.csv";

			        response.setContentType("text/csv");
			        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
			                "attachment; filename=\"" + filename + "\"");
			        
			        Writer writer = new PrintWriter(response.getWriter());
			        StatefulBeanToCsv<ComprobanteFiscal> beanToCsv = new 
			                                      StatefulBeanToCsvBuilder<ComprobanteFiscal>(writer).build();
			       Proveedor proveedor =  proveedorRepository.findByEmpresasAndClaveProv(empresaRepository.findByRfc(rfc), clave).get();
			        beanToCsv.write(comprobanteFiscalRepository.findAllByRfcEmpresaAndProveedor(rfc,proveedor));
			        writer.close();


		        } catch (CsvException ex) {

		            Log.falla("Error al exportar Rerpote CSV para proveedor.", "ERROR_FILE");
		        }
	        
	}

}
