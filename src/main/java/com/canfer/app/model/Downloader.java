package com.canfer.app.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Service
public class Downloader {

	public HttpServletResponse downloadComprobanteFiscalCsv(List<ComprobanteFiscal> comprobantes,
			HttpServletResponse response) {

		// set file name and content type
		Writer writer;
		String filename = "CSVReport.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

		try {

			writer = new PrintWriter(response.getWriter());
			StatefulBeanToCsv<ComprobanteFiscal> beanToCsv = new StatefulBeanToCsvBuilder<ComprobanteFiscal>(writer)
					.build();
			beanToCsv.write(comprobantes);
			writer.close();

		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {

			Log.activity("No se logró generar el reporte CSV.",
					((ComprobanteFiscal) comprobantes.get(0)).getEmpresaNombre(), "ERROR");

		}

		return response;

	}

	public ResponseEntity<byte[]> downloadZip(List<Archivo> files) {

		// checkout for errors and how to display them
		String zipFileName = "ZipReport.zip";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);

		for (Archivo file : files) {

			Resource resource = file.loadAsResource();
			ZipEntry zipEntry = new ZipEntry(resource.getFilename());

			try {

				zipEntry.setSize(resource.contentLength());
				zipOut.putNextEntry(zipEntry);
				StreamUtils.copy(resource.getInputStream(), zipOut);
				zipOut.closeEntry();

			} catch (IOException e) {

				Log.activity("Error al comprimir archivo: " + file.getNombre() + ".", file.getReceptor(), "ERROR_FILE");
			}

		}

		try {
			zipOut.finish();
			zipOut.close();
		} catch (IOException e) {

			Log.activity("Error durante la descarga: No fué posible comprimir los documentos.",
					files.get(0).getReceptor(), "ERROR_STORAGE");

			return ResponseEntity.badRequest().body(bos.toByteArray());
		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"")
				.body(bos.toByteArray());

	}

	public ResponseEntity<Resource> download(Archivo file, String method) {

		String action = null;
		String contentType = "application/pdf";

		switch (method) {

		case "p":

			action = "inline";
			break;

		case "d":

			action = "attachment";
			break;

		}

		if (file.getExtension().equalsIgnoreCase("xml")) {

			contentType = "text/xml";

		} else if (file.getExtension().equalsIgnoreCase("xls")) {

			contentType = "application/xls";
		}

		Resource resource = file.loadAsResource();

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, action + "; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

}
