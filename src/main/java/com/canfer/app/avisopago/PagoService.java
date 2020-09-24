package com.canfer.app.avisopago;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Pago;
import com.canfer.app.repository.ComprobanteFiscalRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.PagoRepository;
import com.canfer.app.service.ComprobanteFiscalService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;




@Service
public class PagoService {

	@Autowired
	PagoRepository pagoRepository; 
	@Autowired
	ComprobanteFiscalService comprobanteFiscalService; 
	@Autowired
	ComprobanteFiscalRepository comprobanteFiscalRepository; 
	@Autowired
	EmpresaRepository empresaRepository; 
		
	public void checkTable() {
		
		List<Pago> listaPagos = pagoRepository.findByBitProcesado(false);
		
		for (Pago pago : listaPagos) {
		    Empresa empresa = empresaRepository.findByRfc(
		    		pago.getRfcEmpresa());
		    ComprobanteFiscal comprobante = comprobanteFiscalRepository.
		    		findByIdNumSapAndEmpresa(pago.getIdNumSap(), empresa);
		    comprobanteFiscalService.updateEstatus(comprobante, pago.getNuevoEstatusFactura());
		}

	}
	
	public void makePdfPago() throws JRException, IOException {
		
		JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/Blank_A4.jrxml");
		JRDataSource  dataSource = new JREmptyDataSource(); 
        // Adding the additional parameters to the pdf.
         Map<String, Object> parameters = new HashMap<>();
        parameters.put("rfcEmisor", ".com");
 
		// Filling the report with the employee data and additional parameters information.
         JasperPrint print = JasperFillManager.fillReport(jasperReport,parameters, dataSource);
 
         JasperExportManager.exportReportToPdfFile(print, "src/main/resources/testJasper.pdf");
       
    }
	
}

