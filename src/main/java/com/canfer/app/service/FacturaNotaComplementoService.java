package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.FacturaNotaComplementoRepository;

@Service
public class FacturaNotaComplementoService {
	
	@Autowired
	FacturaNotaComplementoRepository facturaNotaComplementoRepository;
	private final String documentNotFound = "El documento no existe.";
	
	public List<FacturaNotaComplemento> findAll(){
		
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		
		return facturaNotaComplementoRepository.findAll();		
	}
	
	public FacturaNotaComplemento findById(Long id) {
		Optional<FacturaNotaComplemento> fncDocumento = facturaNotaComplementoRepository.findById(id);
		//Check if the document really exists in the database.
		if (fncDocumento.isEmpty()) {
			throw new DataAccessResourceFailureException(documentNotFound);
		}
		
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		//Logica para rechazar solicitudes no relacionadas al usuario principal.
		
		return fncDocumento.get();
		
	}
	
	public FacturaNotaComplemento findByUUID(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(documentNotFound);
		}
		//Logica para reconocer el usuario activo; devolver documentos pertinentes al usuario
		//Logica para rechazar solicitudes no relacionadas al usuario principal.
		
		return fncDocumento;
	}
	
	public void delete(String uuid) {
		FacturaNotaComplemento fncDocumento = facturaNotaComplementoRepository.findByUuid(uuid);
		//Check if the document really exists in the database.
		if (fncDocumento == null) {
			throw new DataAccessResourceFailureException(documentNotFound);
		}
		
		facturaNotaComplementoRepository.delete(fncDocumento);
	}
	
	public FacturaNotaComplemento save() {
		//Business logic
		//Check for the user principal
		/*--------------------------------*/
		/*   Web service validation        */
		return null;
	}
	
	
	

}
