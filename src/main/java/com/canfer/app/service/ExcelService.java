package com.canfer.app.service;

import java.io.*;
import java.util.List;

import org.springframework.stereotype.Service;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.ComprobanteFiscal;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;




@Service
public class ExcelService {
	
	public ExcelService() {
		// TODO Auto-generated constructor stub
	}
	
    public Archivo makeExcel(List<ComprobanteFiscal> comprobantes) throws IOException, WriteException {

    	File currDir = new File(".");
    	String path = currDir.getAbsolutePath();
    	String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";
    	int count = 2; 

    	WritableWorkbook workbook = Workbook.createWorkbook(new File(fileLocation));

    	WritableSheet sheet = workbook.createSheet("Sheet 1", 0);

    	WritableCellFormat headerFormat = new WritableCellFormat();
    	WritableFont font
    	  = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
    	headerFormat.setFont(font);
    	headerFormat.setBackground(Colour.LIGHT_BLUE);
    	headerFormat.setWrap(true);

    	Label headerLabel = new Label(0, 0, "UUID", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);

    	headerLabel = new Label(1, 0, "No. SAP", headerFormat);
    	sheet.setColumnView(0, 40);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(2, 0, "Serie", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(3, 0, "Folio", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(4, 0, "RFC Emisor", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(5, 0, "RFC Receptor", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(6, 0, "Fecha Emisión", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(7, 0, "Fecha Carga", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(8, 0, "Timbre", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(9, 0, "Moneda", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(10, 0, "Total", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(11, 0, "Tipo de Comprobante", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(12, 0, "Estatus Pago", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(13, 0, "Validación SAT", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(14, 0, "Vigencia", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);
    	
    	headerLabel = new Label(15, 0, "Comentario", headerFormat);
    	sheet.setColumnView(0, 60);
    	sheet.addCell(headerLabel);

    	
    	WritableCellFormat cellFormat = new WritableCellFormat();
    	cellFormat.setWrap(true);
    	
    	for (ComprobanteFiscal cfd : comprobantes) {
    	Label cell = new Label(0, count, cfd.getUuid(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(1, count, cfd.getIdNumSap().toString(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(2, count, cfd.getSerie(), cellFormat);
    	sheet.addCell(cell);
    	
        cell = new Label(3, count, cfd.getFolio(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(4, count, cfd.getRfcProveedor(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(5, count, cfd.getRfcEmpresa(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(6, count, cfd.getFechaEmision(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(7, count, cfd.getFechaCarga().toString(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(8, count, cfd.getVersionTimbre(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(9, count, cfd.getMoneda(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(10, count, cfd.getTotal(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(11, count, cfd.getTipoDocumento(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(12, count, cfd.getEstatusPago(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(13, count, cfd.getRespuestaValidacion(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(14, count, cfd.getEstatusSAT(), cellFormat);
    	sheet.addCell(cell);
    	
    	cell = new Label(15, count, cfd.getComentario(), cellFormat);
    	sheet.addCell(cell);
    	
    	count = count+ 1; 
    	
    	}

    	workbook.write();
    	workbook.close();
    	
    	ArchivoXML file  = new ArchivoXML(fileLocation, "xls", "temp.xls");
    	return file; 
    }
    
}