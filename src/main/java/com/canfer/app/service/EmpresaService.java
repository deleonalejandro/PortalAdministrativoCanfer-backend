package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Municipio;
import com.canfer.app.dto.EmpresaDTO;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.MunicipioRepository;
import com.canfer.app.security.IAuthenticationFacade;
import com.canfer.app.security.UserPrincipal;

import javassist.NotFoundException;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private MunicipioRepository municipioRepository;

	
	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}
	
	public List<Empresa> findAllById(List<Long> ids) {
		return empresaRepository.findAllById(ids);
	}
	
	public Empresa findById(Long id) throws NotFoundException {
		Optional<Empresa> empresa = empresaRepository.findById(id);
		if (empresa.isEmpty()) {
			throw new NotFoundException("La empresa no existe.");
		}
		return empresa.get();
	}
	
	public boolean exist(Long id) {
		Optional<Empresa> deleteEmpresa = empresaRepository.findById(id);
		return deleteEmpresa.isPresent(); 
	}
	
	public Empresa save(EmpresaDTO empresa) {
		/*Add some handle error logic: user authorization? Company exists?*/
		Empresa saveEmpresa;
		
		Empresa checkEmpresa = empresaRepository.findByRfc(empresa.getRfc());
		
		if(checkEmpresa != null) {
			
			throw new EntityExistsException("La empresa que desea registrar ya existe.");
			
		} else {
			
			saveEmpresa = new Empresa();
		}
		
		if (empresa.getNombre().isEmpty() || empresa.getRfc().isEmpty()) {
			
			throw new NullArgumentException("El nombre o el RFC de la empresa no es válido");
			
		}

		try {
			
			//Get user principal to fill the user creation field.
			UserPrincipal userDetails = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
			
			//Transfer the information form the DTO object.
			saveEmpresa.setCalle(empresa.getCalle());
			saveEmpresa.setColonia(empresa.getColonia());
			saveEmpresa.setContacto(empresa.getContacto());
			saveEmpresa.setCorreo(empresa.getCorreo());
			saveEmpresa.setCp(empresa.getCp());
			saveEmpresa.setLocalidad(empresa.getLocalidad());
			saveEmpresa.setNombre(empresa.getNombre());
			saveEmpresa.setNumExt(empresa.getNumExt());
			saveEmpresa.setNumInt(empresa.getNumInt());
			saveEmpresa.setPaginaWeb(empresa.getPaginaWeb());
			saveEmpresa.setReferencia(empresa.getReferencia());
			saveEmpresa.setRfc(empresa.getRfc());
			saveEmpresa.setTelefono(empresa.getTelefono());
			saveEmpresa.setIdUsuarioCreador(userDetails.getUserId());
			saveEmpresa.setProfilePictureName(empresa.getProfilePictureName());
			saveEmpresa.setColor(empresa.getColor());
			
			if (empresa.getIdMunicipio() != null) {
				
				Optional<Municipio> mun = municipioRepository.findById(empresa.getIdMunicipio());
				
				if (mun.isPresent()) {
					saveEmpresa.setMunicipio(mun.get().getNombre());
				}
			}
			
		} catch (Exception e) {
			
			throw new UnknownError("Ocurrio un error inesperado");
			
		}
		
		return empresaRepository.save(saveEmpresa);
		
	}
	
	public void update(EmpresaDTO empresa) {
		
		Empresa saveEmpresa = null;
		
		Empresa checkEmpresa = empresaRepository.findByRfc(empresa.getRfc());
		
		if(checkEmpresa == null) {
			
			throw new EntityExistsException("La empresa que desea actualizar no se encuentra en la base de datos.");
			
		} 
		
		saveEmpresa = checkEmpresa;
		
		try {
			
			//Get user principal to fill the user creation field.
			UserPrincipal userDetails = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
			
			//Transfer the information form the DTO object.
			saveEmpresa.setCalle(empresa.getCalle());
			saveEmpresa.setColonia(empresa.getColonia());
			saveEmpresa.setContacto(empresa.getContacto());
			saveEmpresa.setCorreo(empresa.getCorreo());
			saveEmpresa.setCp(empresa.getCp());
			saveEmpresa.setLocalidad(empresa.getLocalidad());
			saveEmpresa.setNombre(empresa.getNombre());
			saveEmpresa.setNumExt(empresa.getNumExt());
			saveEmpresa.setNumInt(empresa.getNumInt());
			saveEmpresa.setPaginaWeb(empresa.getPaginaWeb());
			saveEmpresa.setReferencia(empresa.getReferencia());
			saveEmpresa.setRfc(empresa.getRfc());
			saveEmpresa.setTelefono(empresa.getTelefono());
			saveEmpresa.setIdUsuarioCreador(userDetails.getUserId());
			saveEmpresa.setColor(empresa.getColor());
			
			if (!empresa.getProfilePictureName().isEmpty()) {
				
				saveEmpresa.setProfilePictureName(empresa.getProfilePictureName());
				
			}
			
			if (empresa.getIdMunicipio() != null) {
				
				Optional<Municipio> mun = municipioRepository.findById(empresa.getIdMunicipio());
				
				if (mun.isPresent()) {
					saveEmpresa.setMunicipio(mun.get().getNombre());
				}
			}
			
			empresaRepository.save(saveEmpresa);
			
		} catch (Exception e) {
			
			throw new UnknownError("Ocurrio un error inesperado");
			
		}
		
		
		
	}
	
	public void delete(Long id) throws NotFoundException {
		// check if Empresa exists
		if (!exist(id)) {
			throw new NotFoundException("La empresa que desea eliminar no existe.");	
		}
		Empresa empresa = findById(id);
		empresaRepository.deleteById(id);
		
		Log.activity("Se eliminó una empresa." , empresa.getNombre(), "DELETE");
	}


}
