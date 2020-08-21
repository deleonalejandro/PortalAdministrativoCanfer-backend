package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Empresa;
import com.canfer.app.dto.EmpresaDTO;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.MunicipioRepository;
import com.canfer.app.security.IAuthenticationFacade;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	public List<Empresa> findAll(){
		return empresaRepository.findAll();
	}
	
	public Empresa findById(Long id) throws Exception {
		Optional<Empresa> empresa = empresaRepository.findById(id);
		if (empresa.isEmpty()) {
			throw new Exception("La empresa no existe.");
		}
		return empresa.get();
	}
	
	public Empresa save(EmpresaDTO empresa) {
		/*Add some handle error logic: user authorization? Company exists?*/
		Empresa saveEmpresa = new Empresa();
		Optional<Empresa> checkEmpresa = empresaRepository.findById(empresa.getIdEmpresa());
		if (checkEmpresa.isPresent()) {
			saveEmpresa = checkEmpresa.get();
		}
		//Get user principal tu fill the usuario crador field.
		Usuario principal = (Usuario)authenticationFacade.getAuthentication().getPrincipal();
		System.out.println(principal.getNombre());
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
		saveEmpresa.setidUsuarioCreador(principal.getIdUsuario());
		//We add the object using references
		saveEmpresa.setMunicipio(municipioRepository.getOne(empresa.getIdMunicipio()));
		
		return empresaRepository.save(saveEmpresa);
		
	}
	
	public void delete(Long id) throws Exception {
		Optional<Empresa> deleteEmpresa = empresaRepository.findById(id);
		if (deleteEmpresa.isEmpty()) {
			throw new Exception("La empresa no existe.");	
		}
		
		empresaRepository.delete(deleteEmpresa.get());
	}
	

}
