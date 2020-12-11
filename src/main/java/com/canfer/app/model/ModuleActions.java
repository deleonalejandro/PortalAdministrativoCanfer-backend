package com.canfer.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.PagoRepository;
import com.opencsv.exceptions.CsvException;

import javassist.NotFoundException;

public abstract class ModuleActions {
	
	
	@Autowired
	protected PagoRepository pagoRepo;
	
	@Autowired
	protected ComprobanteFiscalRespository comprobanteRepo;
	
	@Autowired
	protected Downloader dowloadManager;

	public ModuleActions() {
		
	}
	
	public abstract boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF) throws FileExistsException, NotFoundException;
	
	public abstract boolean delete(Long id);
	
	public abstract boolean deleteAll(List<Long> ids);
	
	public abstract void downloadCsv(List<Long> ids, HttpServletResponse response) throws CsvException, IOException;
	
	
	
	public ResponseEntity<Resource> download(String method, String repo, Long id, String action) {
	
		Optional<Pago> valuePago;
		Optional<ComprobanteFiscal> valueComprobante;
		IModuleEntity entity = null;
		ComprobanteFiscal comprobante;
		
		
		// switching the repository strategies for single files.
		switch (repo) {
		
		case "Pago":
			
			valuePago = pagoRepo.findById(id);
			
			if (valuePago.isPresent()) {
				
				entity = valuePago.get();
			}
				
			break;
			
		case "ComprobanteFiscal":
			
			valueComprobante =  comprobanteRepo.findById(id);
			
			if (valueComprobante.isPresent()) {
				
				entity = valueComprobante.get();
			}
			
			break;

		default:
			break;
		}
		
		// switching the method strategies for single files.
		switch (method) {
		
		case "singleXML":
			
			return dowloadManager.download(entity.fetchXML(), action);

		case "singlePDF":
			
			return dowloadManager.download(entity.fetchPDF(), action);
			
		case "singlePayment":
			
			comprobante = (ComprobanteFiscal) entity;
			
			return dowloadManager.download(comprobante.getPago().fetchPDF(), action);
			
		case "singleComplemento":
			
			comprobante = (ComprobanteFiscal) entity;
			
			return dowloadManager.download(((Factura) comprobante).getComplemento().fetchXML(), action);
			
		default:
			break;
			
		}
		
		return null;
	}
	
	public ResponseEntity<byte[]> download(String method, String repo, List<Long> ids) {
		
		List<Archivo> files = new ArrayList<>();
		List<ComprobanteFiscal> comprobantes = new ArrayList<>();
		
		// switching the repository strategies for single files.
		switch (repo) {

		case "ComprobanteFiscal":
			
			comprobantes = comprobanteRepo.findAllById(ids);
			
			break;

		default:
			break;
		}
		
		switch (method) {
		
		case "zipXML":
			
			for (ComprobanteFiscal comprobanteFiscal : comprobantes) {
				
				files.add(comprobanteFiscal.fetchXML());
				
			}
			
			break;
			
		case "zipPDF":
			
			for (ComprobanteFiscal comprobanteFiscal : comprobantes) {

				files.add(comprobanteFiscal.fetchPDF());

			}

			break;
			
		case "zip":
			
			for (ComprobanteFiscal comprobanteFiscal : comprobantes) {
				
				files.add(comprobanteFiscal.fetchXML());
				files.add(comprobanteFiscal.fetchPDF());

			}

			break;

		default:
			break;
		}
			
		return dowloadManager.downloadZip(files);
		
	}






}
