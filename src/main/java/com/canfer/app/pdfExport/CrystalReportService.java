package com.canfer.app.pdfExport;

//Crystal Java Reporting Component (JRC) imports.
import com.crystaldecisions.reports.sdk.*;
import com.crystaldecisions.sdk.occa.report.lib.*;
import com.crystaldecisions.sdk.occa.report.exportoptions.*;

//Java imports.
import java.io.*;

import org.springframework.stereotype.Service;

@Service
public class CrystalReportService {

	 String REPORT_NAME = "C:\\Users\\aadministrador\\Desktop\\AVISO_PAGO_PAECRSAP-JDBC .rpt";
	 String EXPORT_FILE = "C:\\Users\\aadministrador\\Desktop\\ExportedReport.pdf";
	
	public String exportPDF(String empresa, Integer pago, String user, String password) {

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
			File file = new File(EXPORT_FILE);
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteArrayInputStream.available());
			int x = byteArrayInputStream.read(byteArray, 0, byteArrayInputStream.available());

			byteArrayOutputStream.write(byteArray, 0, x);
			byteArrayOutputStream.writeTo(fileOutputStream);

			//Close streams.
			byteArrayInputStream.close();
			byteArrayOutputStream.close();
			fileOutputStream.close();
			
			System.out.println("Successfully exported report to " + EXPORT_FILE);
								
		}
		catch(ReportSDKException ex) {
		
			ex.printStackTrace();
			
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
						
		}
		
		return EXPORT_FILE;

	}

}

   