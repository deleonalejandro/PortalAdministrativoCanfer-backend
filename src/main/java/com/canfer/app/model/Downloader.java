package com.canfer.app.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

public class Downloader {

	public ResponseEntity<Resource> download(Archivo file) {

		String contentType = "application/octet-stream";

		// try to load resource
		Resource resource = file.loadAsResource();

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getNombre() + "\"")
				.body(resource);

	}

	public HttpServletResponse downloadCSV(List<Object> entradas, HttpServletResponse response)
			throws CsvException, IOException {

		// set file name and content type
		String filename = "CSVReport.csv";

		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

		Writer writer = new PrintWriter(response.getWriter());
		StatefulBeanToCsv<Object> beanToCsv = new StatefulBeanToCsvBuilder<Object>(writer).build();
		beanToCsv.write(entradas);
		writer.close();

		return response;

	}

	public ResponseEntity<byte[]> downloadZip(List<Archivo> files) throws IOException {

		// checkout for errors and how to display them
		String zipFileName = "ZipReport.zip";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);

		for (Archivo file : files) {

			FileSystemResource resource = (FileSystemResource) file.loadAsResource();
			ZipEntry zipEntry = new ZipEntry(resource.getFilename());

			zipEntry.setSize(resource.contentLength());
			zipOut.putNextEntry(zipEntry);
			StreamUtils.copy(resource.getInputStream(), zipOut);
			zipOut.closeEntry();

		}

		zipOut.finish();
		zipOut.close();

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"")
				.body(bos.toByteArray());

	}

}
