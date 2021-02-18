package com.canfer.app.service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.DetFormularioCajaChica;
import com.canfer.app.model.FormularioCajaChica;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@Service
public class ExcelService {

	@Autowired
	private RepositoryService superRepo;

	public ExcelService() {
		// TODO Auto-generated constructor stub
	}

	public Archivo makeExcel(List<ComprobanteFiscal> comprobantes) throws IOException, WriteException {

		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "Reporte.xls";
		int count = 2;

		WritableWorkbook workbook = Workbook.createWorkbook(new File(fileLocation));

		WritableSheet sheet = workbook.createSheet("Reporte Listado CFDIs", 0);

		WritableCellFormat headerFormatC = new WritableCellFormat();
		WritableFont fontc = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD);
		headerFormatC.setFont(fontc);
		headerFormatC.setWrap(true);
		headerFormatC.setBackground(Colour.GOLD);

		Label headerLabelc = new Label(0, 0, "Reporte Documentos Fiscales", headerFormatC);
		sheet.setColumnView(0, 60);
		sheet.addCell(headerLabelc);

		WritableCellFormat headerFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
		headerFormat.setFont(font);
		headerFormat.setWrap(true);

		Label headerLabel = new Label(0, 1, "UUID", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(1, 1, "No. SAP", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(2, 1, "Serie", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(3, 1, "Folio", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(4, 1, "RFC Emisor", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(5, 1, "RFC Receptor", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(6, 1, "Fecha Emisión", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(7, 1, "Fecha Carga", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(8, 1, "Timbre", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(9, 1, "Moneda", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(10, 1, "Total", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(11, 1, "Tipo de Comprobante", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(12, 1, "Estatus Pago", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(13, 1, "Validación SAT", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(14, 1, "Vigencia", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(15, 1, "Comentario", headerFormat);
		sheet.addCell(headerLabel);

		for (int c = 0; c < 15; c++) {
			CellView cell = sheet.getColumnView(c);
			cell.setAutosize(true);
			sheet.setColumnView(c, cell);
		}

		WritableCellFormat cellFormatWrap = new WritableCellFormat();
		cellFormatWrap.setWrap(true);
		cellFormatWrap.setAlignment(Alignment.CENTRE);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);

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

			cell = new Label(6, count, cfd.getFechaEmision(), cellFormatWrap);
			sheet.addCell(cell);

			cell = new Label(7, count, cfd.getFechaCarga().toString(), cellFormatWrap);
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

			cell = new Label(13, count, cfd.getRespuestaValidacion(), cellFormatWrap);
			sheet.addCell(cell);

			cell = new Label(14, count, cfd.getEstatusSAT(), cellFormat);
			sheet.addCell(cell);

			cell = new Label(15, count, cfd.getComentario(), cellFormatWrap);
			sheet.addCell(cell);

			count = count + 1;

		}

		sheet.setColumnView(9, 30);
		sheet.setColumnView(15, 30);

		WritableCellFormat cellFormatF = new WritableCellFormat();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		Label cell = new Label(0, count + 1, "Reporte generado el : " + dtf.format(now), cellFormatF);
		sheet.addCell(cell);

		workbook.write();
		workbook.close();

		ArchivoXML file = new ArchivoXML(fileLocation, "xls", "temp.xls");
		return file;
	}

	public Archivo makeExcel(FormularioCajaChica formulario) throws IOException, WriteException {

		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "Reporte.xls";

		WritableWorkbook workbook = Workbook.createWorkbook(new File(fileLocation));

		WritableSheet sheet = workbook.createSheet("Reporte Formulario Caja Chica", 0);

		WritableCellFormat headerFormatC = new WritableCellFormat();
		WritableFont fontc = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD);
		headerFormatC.setFont(fontc);
		headerFormatC.setWrap(true);
		headerFormatC.setBackground(Colour.GOLD);

		Label headerLabelc = new Label(0, 0, "Reporte Formulario  Caja Chica", headerFormatC);
		sheet.setColumnView(0, 60);
		sheet.addCell(headerLabelc);

		WritableCellFormat headerFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
		headerFormat.setFont(font);
		headerFormat.setWrap(true);

		Label headerLabel = new Label(0, 1, "Formulario", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(0, 2, "Folio", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(0, 3, "Socio de Negocio", headerFormat);
		sheet.addCell(headerLabel);

		headerLabel = new Label(0, 4, "Fecha", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(0, 5, "Comentario", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(0, 7, "Detalles", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(0, 8, "#", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(1, 8, "Folio", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(2, 8, "Clasificación", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(3, 8, "Beneficiario", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(4, 8, "Fecha", headerFormat);
		sheet.addCell(headerLabel);
		
		headerLabel = new Label(5, 8, "Monto", headerFormat);
		sheet.addCell(headerLabel);
		
		
		

		CellView cell = sheet.getColumnView(0);
		cell.setAutosize(true);
		sheet.setColumnView(0, cell);

		WritableCellFormat cellFormatWrap = new WritableCellFormat();
		cellFormatWrap.setWrap(true);
		cellFormatWrap.setAlignment(Alignment.CENTRE);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);

		List<DetFormularioCajaChica> detalles = superRepo.findAllDetFormularioCajaChicaByFormCC(formulario);

		int count = 1;
		for (DetFormularioCajaChica detalle : detalles) {
			
			Label celllabel = new Label(0, count+8, String.valueOf(count), cellFormat);
			sheet.addCell(celllabel);
			
			celllabel = new Label(1, count+8, detalle.getFolio(), cellFormat);
			sheet.addCell(celllabel);

			celllabel = new Label(2, count+8, detalle.getClasificacion().getClasificacion(), cellFormat);
			sheet.addCell(celllabel);

			celllabel = new Label(3, count+8, detalle.getBeneficiario(), cellFormat);
			sheet.addCell(celllabel);

			celllabel = new Label(4, count+8, detalle.getFecha().toString(), cellFormat);
			sheet.addCell(celllabel);

			celllabel = new Label(5, count+8, detalle.getMonto().toString(), cellFormat);
			sheet.addCell(celllabel);

			count = count + 1;

		}
		
		headerLabel = new Label(4, count+9, "Total: ", headerFormat);
		sheet.addCell(headerLabel);
		headerLabel = new Label(5, count+9, "$"+formulario.getTotal().toString(), headerFormat);
		sheet.addCell(headerLabel);
		

		WritableCellFormat cellFormatF = new WritableCellFormat();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		Label celllabel1 = new Label(0, count + 10, "Reporte generado el : " + dtf.format(now), cellFormatF);
		sheet.addCell(celllabel1);

		workbook.write();
		workbook.close();

		ArchivoXML file = new ArchivoXML(fileLocation, "xls", "temp.xls");
		return file;
	}

}