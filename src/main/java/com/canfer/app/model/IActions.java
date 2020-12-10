package com.canfer.app.model;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface IActions {
	
	public boolean upload(Archivo xml, Archivo pdf);
	
	public ResponseEntity<Object> download(String method, String repo, Long id);
	
	public ResponseEntity<Object> download(List<Long> ids);
	
	public Resource preview(Long id);

	public boolean delete(Long id);
	

}
