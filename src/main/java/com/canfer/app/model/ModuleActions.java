package com.canfer.app.model;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.service.RepositoryService;

import javassist.NotFoundException;

@Service
public abstract class ModuleActions {
	
	@Autowired
	protected RepositoryService superRepo;
	
	@Autowired
	protected Downloader dowloadManager;


	protected abstract boolean upload(ArchivoXML fileXML, ArchivoPDF filePDF) throws FileExistsException, NotFoundException;
	
	protected abstract boolean delete(Long id);
	
	protected abstract boolean deleteAll(List<Long> ids);
	
	protected abstract ResponseEntity<Resource> download(String method, String repo, Long id, String action);
	
	protected abstract ResponseEntity<byte[]> download(String method, String repo, List<Long> ids);
	
	public void downloadCsv(List<Long> ids, HttpServletResponse response) {

		List<ComprobanteFiscal> comprobantes = superRepo.findAllComprobanteById(ids);
		
		dowloadManager.downloadComprobanteFiscalCsv(comprobantes, response);
		
	}






}
