package com.canfer.app.async;

//Crystal Java Reporting Component (JRC) imports.
import com.crystaldecisions.reports.sdk.*;
import com.crystaldecisions.sdk.occa.report.lib.*;
import com.crystaldecisions.sdk.occa.report.exportoptions.*;

//Java imports.
import java.io.*;

import org.springframework.stereotype.Service;

@Service
public class CrystalReportService {

	static final String REPORT_NAME = "Report2.rpt";
	static final String EXPORT_FILE = "C:\\Users\\alex2\\PortalProveedores\\ExportedPDFs\\myExportedReport.pdf";
	
	public void exportPDF() {

		try {

			//Open report.			
			ReportClientDocument reportClientDoc = new ReportClientDocument();			
			reportClientDoc.open(REPORT_NAME, 0);
			
			//NOTE: If parameters or database login credentials are required, they need to be set before.
			//calling the export() method of the PrintOutputController.
			
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

	}

}

   