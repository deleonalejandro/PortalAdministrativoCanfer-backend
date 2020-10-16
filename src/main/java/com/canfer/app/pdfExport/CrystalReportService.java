package com.canfer.app.pdfExport;

import com.canfer.app.model.Documento;
import com.canfer.app.model.Documento.DocumentoPDF;
import com.canfer.app.model.Log;
import com.canfer.app.repository.DocumentoRepository;
import com.canfer.app.repository.EmpresaRepository;
//Crystal Java Reporting Component (JRC) imports.
import com.crystaldecisions.reports.sdk.*;
import com.crystaldecisions.sdk.occa.report.lib.*;
import com.crystaldecisions.sdk.occa.report.exportoptions.*;

//Java imports.
import java.io.*;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrystalReportService {
	
	@Autowired
	private EmpresaRepository empresaRepository; 
	@Autowired
	private DocumentoRepository documentoRepository; 
	
	public String exportPDF(String empresa, Integer pago, String user, String password, String rfc, Long idTabla) {

		String REPORT_NAME = "C:\\Users\\aadministrador\\Desktop\\AVISO_PAGO_PAECRSAP-JDBC .rpt";
		 String EXPORT_FILE = "C:\\Users\\alex2\\PortalProveedores\\ExportedPDFs";
		 
		try {

			//Open report.			
			ReportClientDocument reportClientDoc = new ReportClientDocument();			
			reportClientDoc.open(REPORT_NAME, 0);
			
			//NOTE: If parameters or database login credentials are required, they need to be set before.
			//calling the export() method of the PrintOutputController.
			
			//Incluir Parametros
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "Empresa", empresa);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "Pago", pago);
			
			//Incluir DB login
			reportClientDoc.getDatabaseController().logon(user, password);
			
			//Export report and obtain an input stream that can be written to disk.
			//See the Java Reporting Component Developer's Guide for more information on the supported export format enumerations
			//possible with the JRC.
			ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream)reportClientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
			
			//Release report.
			reportClientDoc.close();
						
			//Use the Java I/O libraries to write the exported content to the file system.
			byte byteArray[] = new byte[byteArrayInputStream.available()];

			//Create a new file that will contain the exported result.
			File file = new File(EXPORT_FILE + File.separator + rfc + File.separator + pago + ".pdf");
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteArrayInputStream.available());
			int x = byteArrayInputStream.read(byteArray, 0, byteArrayInputStream.available());

			byteArrayOutputStream.write(byteArray, 0, x);
			byteArrayOutputStream.writeTo(fileOutputStream);

			//Close streams.
			byteArrayInputStream.close();
			byteArrayOutputStream.close();
			fileOutputStream.close();
			
			//TODO rutas del crystal 
			//Guardamos el Crystal
 			DocumentoPDF doc = new DocumentoPDF(idTabla, empresaRepository.findByRfc(rfc),"Documentos Fiscales", "Aviso de pago", 
					"pdf", EXPORT_FILE + File.separator + rfc + File.separator + pago + ".pdf");
			documentoRepository.save(doc);
			
			return EXPORT_FILE + File.separator + rfc + File.separator + pago + ".pdf";			
		}
		catch(ReportSDKException ex) {
		
			Log.falla("No se pudo generar el Crystal Report para el Pago: " + pago );
			return null; 
		}
		catch(Exception ex) {
			
			Log.falla("Ocurrió un error al exportar el reporte.");
			ex.printStackTrace();
			return null; 	
		}
		
		

	}

	public String exportGenerico(Long id, String rfcEmisor, String nombreEmisor, String folio, String rfcReceptor, String nombreReceptor,
			String usoCFDI, String uuid, String csd, String serie, String fecha, String tipo, String regimen) {

		 String REPORT_NAME = "C:\\Users\\aadministrador\\Desktop\\pdfGenerico.rpt";
		 String EXPORT_FILE = "C:\\Users\\aadministrador\\Desktop\\PDFGenerico";
		 
		try {

			//Open report.			
			ReportClientDocument reportClientDoc = new ReportClientDocument();			
			reportClientDoc.open(REPORT_NAME, 0);
			
			//NOTE: If parameters or database login credentials are required, they need to be set before.
			//calling the export() method of the PrintOutputController.
			
			//Incluir Parametros
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "rfcEmisor", rfcEmisor);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "nombreEmisor", nombreEmisor);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "folio", folio);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "rfcReceptor", rfcReceptor);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "nombreReceptor", nombreReceptor);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "usoCFDI", usoCFDI);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "uuid", uuid);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "csd", csd);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "serie", serie);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "emision", fecha);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "tipo", tipo);
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "regimen", regimen);
			
			
			//save QR
			URL url = new URL("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=CFDI");
			InputStream in = new BufferedInputStream(url.openStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf)))
			{
			   out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();
			
			FileOutputStream fos = new FileOutputStream("C:\\Users\\aadministrador\\Desktop\\api.qrserver.png");
			fos.write(response);
			fos.close();
			
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "qr", "C:\\Users\\aadministrador\\Desktop\\api.qrserver.png");
			
			//Export report and obtain an input stream that can be written to disk.
			//See the Java Reporting Component Developer's Guide for more information on the supported export format enumerations
			//possible with the JRC.
			ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream)reportClientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
			
			//Release report.
			reportClientDoc.close();
						
			//Use the Java I/O libraries to write the exported content to the file system.
			byte byteArray[] = new byte[byteArrayInputStream.available()];

			//Create a new file that will contain the exported result.
			File file = new File(EXPORT_FILE + File.separator + folio + ".pdf");
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteArrayInputStream.available());
			int x = byteArrayInputStream.read(byteArray, 0, byteArrayInputStream.available());

			byteArrayOutputStream.write(byteArray, 0, x);
			byteArrayOutputStream.writeTo(fileOutputStream);

			//Close streams.
			byteArrayInputStream.close();
			byteArrayOutputStream.close();
			fileOutputStream.close();
			
			//Guardamos el PDF Generico
			DocumentoPDF doc = new DocumentoPDF(id, empresaRepository.findByRfc(rfcReceptor),"Documentos Fiscales", uuid, 
					"pdf", EXPORT_FILE + File.separator  + folio + ".pdf");
			documentoRepository.save(doc);
			
			return EXPORT_FILE + File.separator  + folio + ".pdf";			
		}
		catch(ReportSDKException ex) {
		
			Log.falla("No se pudo generar el PDF Generico para: " + uuid );
			return null; 
		}
		catch(Exception ex) {
			
			Log.falla("Ocurrió un error al exportar el reporte.");
			ex.printStackTrace();
			return null; 	
		}
		
		

	}
	
}


   