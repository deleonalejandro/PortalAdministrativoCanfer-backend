package com.canfer.app.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.canfer.app.repository.PagoRepository;

public class ModuleActions implements IActions {
	
	private Downloader dowloadManager = new Downloader();
	
	@Autowired
	private PagoRepository pagoRepo;
	
	public ModuleActions() {
	}
	
	@Override
	public boolean upload(Archivo xml, Archivo pdf) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Resource preview(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResponseEntity<Object> download(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Object> download(String method, String repo, Long id) {
		
		Optional<Pago> entity;
		
		switch (method) {
		case "Pago":
			entity = pagoRepo.findById(id);
			break;

		default:
			break;
		}
		
		return null;
	}



}
