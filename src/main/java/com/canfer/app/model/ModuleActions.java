package com.canfer.app.model;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.service.RepositoryService;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.storage.StorageProperties;

import javassist.NotFoundException;

public abstract class ModuleActions {
	
	@Autowired
	protected RepositoryService superRepo;
	
	@Autowired
	protected Downloader dowloadManager;
	
	@Autowired
	protected ComprobanteStorageService comprobanteStorageService;
	
	@Autowired
	protected EmailSenderService emailSender;
	
	@Autowired
	protected StorageProperties storageProperties;


	protected abstract boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF) throws FileExistsException, NotFoundException;
	
	protected abstract boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF, Long idSucursal)
			throws FileExistsException, NotFoundException;

	protected abstract boolean delete(Long id);
	
	protected abstract boolean deleteAll(List<Long> ids);
	
	protected abstract ResponseEntity<Resource> download(String method, String repo, Long id, String action);
	
	protected abstract ResponseEntity<byte[]> download(String method, String repo, List<Long> ids);
	
	public void downloadCsv(List<Long> ids, HttpServletResponse response) {

		List<ComprobanteFiscal> comprobantes = superRepo.findAllComprobanteById(ids);
		
		dowloadManager.downloadComprobanteFiscalCsv(comprobantes, response);
		
	}








}
