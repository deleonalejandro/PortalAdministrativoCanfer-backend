package com.canfer.app.model;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class ModuleActions implements IActions {
	
	private Downloader dowloadManager = new Downloader();
	
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
		// TODO Auto-generated method stub
		return null;
	}



}
