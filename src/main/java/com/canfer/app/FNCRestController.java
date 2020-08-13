package com.canfer.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.FacturaNotaComplementoRepository;

@RestController
public class FNCRestController {


	@Autowired
	private FacturaNotaComplementoRepository repository;
	
	
	@GetMapping("/contaduria-nacional/facturas")
	public  List<FacturaNotaComplemento> getAll() {
	    return repository.findAll();
	  }
	  
	
	@GetMapping("/contaduria-nacional/factura/{id}")
	public FacturaNotaComplemento getOne(String id) {
      return repository.findByUuid(id);
	  }
	
	@GetMapping("contaduria-nacional/delete/{id}")
	public void deleteEmployee(@PathVariable String id) {
		repository.delete(repository.findByUuid(id));
	   
	  }
}