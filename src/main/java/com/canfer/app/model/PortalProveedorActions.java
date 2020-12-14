package com.canfer.app.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.canfer.app.service.ExcelService;
import com.canfer.app.service.RepositoryService;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import jxl.write.WriteException;

@Service("PortalProveedorActions")
public class PortalProveedorActions extends DocumentosNacionalesActions {
	
	@Autowired
	RepositoryService repoService; 
	@Autowired
	ExcelService xlsService; 
	@Autowired
	private Downloader downloader;
	
	 public void downloadCsv(HttpServletResponse response, String rfc, String clave) {
	 	
		 try {
			//set file name and content type
		        String filename = "CFDIs.csv";

		        response.setContentType("text/csv");
		        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
		                "attachment; filename=\"" + filename + "\"");
		        
		        Writer writer = new PrintWriter(response.getWriter());
		        StatefulBeanToCsv<ComprobanteFiscal> beanToCsv = new 
		                                      StatefulBeanToCsvBuilder<ComprobanteFiscal>(writer).build();
		        
		        Empresa empresa = repoService.findEmpresaByRFC(rfc); 
		        Optional<Proveedor> proveedor = repoService.findProveedorByEmpresaAndClaveProv(
		    		   empresa, clave);
		       
		       
		       if (proveedor.isPresent()) {
		    	   
		        beanToCsv.write(repoService.findAllComprobanteByRfcEmpresaAndProveedor(rfc,proveedor.get()));
		        writer.close();
		        
		       }
		       
		 } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException  e) {

			Log.falla("No se logró generar el reporte CSV para clave de proveedor " + clave + " y Empresa " + rfc + " .", "ERROR");
			
		} 

	 }
	public ResponseEntity<Resource> downloadXls(String rfc, String clave) {
		

       Empresa empresa = repoService.findEmpresaByRFC(rfc); 
       Optional<Proveedor> proveedor = repoService.findProveedorByEmpresaAndClaveProv(
    		   empresa, clave);
       
       List<ComprobanteFiscal> comprobantes = repoService.findAllComprobanteByRfcEmpresaAndProveedor(rfc,proveedor.get());
       
       try {
			
			Archivo file = xlsService.makeExcel(comprobantes);
			file.loadAsResource();
			return downloader.download(file,"d");
			
		} catch (WriteException | IOException e) {
			
			Log.activity("Error al intentar generar un reporte de Excel. ", comprobantes.get(0).getEmpresaNombre(), "ERROR_FILE"); 
			return null;
		}
		
	}

}
