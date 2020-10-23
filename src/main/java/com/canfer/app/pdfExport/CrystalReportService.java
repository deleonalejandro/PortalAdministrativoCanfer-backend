package com.canfer.app.pdfExport;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//TODO COMPROBAR QUE TODAS LAS RUTAS ESTEN CORRECTAS

@Service
public class CrystalReportService {
	
	@Autowired
	private EmpresaRepository empresaRepository; 
	@Autowired
	private DocumentoRepository documentoRepository; 
	@Autowired
	private XmlService xmlService; 
	
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
			String path = EXPORT_FILE + File.separator + rfc + File.separator + pago + ".pdf";
					
 			DocumentoPDF doc = new DocumentoPDF(idTabla, empresaRepository.findByRfc(rfc),"Documentos Fiscales", "Aviso de Pago", 
					"pdf", path);
			documentoRepository.save(doc);
			
			return path;			
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

	public String exportGenerico(Long id, String uuid) {

		String REPORT_NAME = "C:\\Users\\aadministrador\\Desktop\\pdfGenerico.rpt";
		String EXPORT_FILE = "C:\\Users\\aadministrador\\Desktop\\PDFGenerico";

		 
		Documento doc = documentoRepository.findByIdTablaAndExtension(id, "xml");
		Comprobante comprobante = xmlService.xmlToObject(Paths.get(doc.getRuta()));
		try {

			//Open report.			
			ReportClientDocument reportClientDoc = new ReportClientDocument();			
			reportClientDoc.open(REPORT_NAME, 0);
			
			//NOTE: If parameters or database login credentials are required, they need to be set before.
			//calling the export() method of the PrintOutputController.
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "folio", comprobante.getFolio());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "serie", "HOLA");
			
			//Incluir Parametros
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "rfcEmisor", comprobante.getEmisorRfc());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "nombreEmisor", comprobante.getEmisorNombre());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "rfcReceptor", comprobante.getReceptorRfc());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "nombreReceptor", comprobante.getReceptorNombre());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "usoCFDI", comprobante.getReceptorUsoCFDI());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "uuid", comprobante.getUuidTfd());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "csd", comprobante.getSelloCfdTfd());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "emision", comprobante.getFecha());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "tipo", comprobante.getTipoDeComprobante());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "regimen", comprobante.getEmisorRegimenFiscal());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "moneda", comprobante.getMoneda());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "formaPago", comprobante.getFormaPago());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "total", comprobante.getTotal());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "sellocfdi", comprobante.getSelloCfdTfd());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "sellosat", comprobante.getSelloSatTfd());
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "sello", comprobante.getSello());
			
			
			String sello = comprobante.getSelloCfdTfd();
			String ultimosDig = sello.substring(sello.length() - 8);
			
			
			//save QR
			String pathQR = "C:\\Users\\alex2\\Desktop\\CurrentQR.png";
			String urlSAT = "https://verificacfdi.facturaelectronica.sat.gob.mx/default.aspx?id="+comprobante.getUuidTfd()+
					"&re="+comprobante.getEmisorRfc()+"&rr="+comprobante.getReceptorRfc()+"&tt="+comprobante.getTotal()+"&fe="+ultimosDig;
			try {
	            generateQRCodeImage(urlSAT, 350, 350, pathQR);
	        } catch (WriterException e) {
	        	
	            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
	        } catch (IOException e) {
	            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
	        }
			
			
			reportClientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "qr", pathQR);
			
			//Export report and obtain an input stream that can be written to disk.
			//See the Java Reporting Component Developer's Guide for more information on the supported export format enumerations
			//possible with the JRC.
			ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream)reportClientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
			
			//Release report.
			reportClientDoc.close();
						
			//Use the Java I/O libraries to write the exported content to the file system.
			byte byteArray[] = new byte[byteArrayInputStream.available()];

			//Create a new file that will contain the exported result.
			String path = EXPORT_FILE + File.separator  + uuid + ".pdf";
			File file = new File(path);
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
			DocumentoPDF documento = new DocumentoPDF(id, empresaRepository.findByRfc(comprobante.getReceptorRfc()),"Documentos Fiscales", uuid, 
					"pdf", path);
			documentoRepository.save(documento);
			
			return path;			
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
	

	    private void generateQRCodeImage(String text, int width, int height, String filePath)
	            throws WriterException, IOException {
	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
	        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

	        Path path = FileSystems.getDefault().getPath(filePath);
	        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	    }
}


   