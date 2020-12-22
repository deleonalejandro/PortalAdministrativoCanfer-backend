package com.canfer.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Municipio;
import com.canfer.app.model.Proveedor;
import com.canfer.app.dto.ProveedorDTO;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.MunicipioRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.security.IAuthenticationFacade;
import com.canfer.app.security.UserPrincipal;

import javassist.NotFoundException;

@Service
public class ProveedorService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private ProveedorRepository proveedorRepository;
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private MunicipioRepository municipioRepository;
	
	public List<Proveedor> findAll() {
		return proveedorRepository.findAll();
	}
	
	public List<Proveedor> findAllById(List<Long> ids) {
		return proveedorRepository.findAllById(ids);
	}
	
	public Proveedor findById(Long id) throws NotFoundException {
		Optional<Proveedor> proveedor = proveedorRepository.findById(id);
		if (proveedor.isEmpty()) {
			throw new NotFoundException("El proveedor no existe en el catalogo.");
		}
		return proveedor.get();
	}
	
	public boolean exist(Long id) {
		Optional<Proveedor> proveedor = proveedorRepository.findById(id);
		return proveedor.isPresent(); 
	}
	
	public Proveedor save(ProveedorDTO proveedor) {
		
		Proveedor saveProveedor;
		Optional<Empresa> empresa;
		List<Empresa> empresas = new ArrayList<>();
		
		if (proveedor.getIdEmpresa() == null) {
			throw new NullArgumentException("El proveedor debe tener una empresa asignada.");
		}
		
		if (proveedor.getRfc().isEmpty() || proveedor.getClaveProv().isEmpty()) {
			throw new NullArgumentException("La clave de proveedor o el RFC no es válido");
		}

		empresa = empresaRepository.findById(proveedor.getIdEmpresa());
		
		if (empresa.isPresent()) {
			
			Optional<Proveedor> checkProveedor = proveedorRepository.findByEmpresasAndClaveProv(empresa.get(), proveedor.getClaveProv());
			
			if(checkProveedor.isPresent()) {
				throw new EntityExistsException("La proveedor que desea registrar ya existe en el catálogo.");
			} 
			
		} 
		
		empresas.add(empresa.get());
		
		//Get user principal to fill the user creation field.
		UserPrincipal userDetails = (UserPrincipal) authenticationFacade.getAuthentication().getPrincipal();
		saveProveedor = new Proveedor(userDetails.getUserId());
	
		
		try {
			
			//Transfer the information form the DTO object.
			saveProveedor.setCalle(proveedor.getCalle());
			saveProveedor.setColonia(proveedor.getColonia());
			saveProveedor.setContacto(proveedor.getContacto());
			saveProveedor.setCorreo(proveedor.getCorreo());
			saveProveedor.setCp(proveedor.getCp());
			saveProveedor.setLocalidad(proveedor.getLocalidad());
			saveProveedor.setNombre(proveedor.getNombre());
			saveProveedor.setNumExt(proveedor.getNumExt());
			saveProveedor.setNumInt(proveedor.getNumInt());
			saveProveedor.setPaginaWeb(proveedor.getPaginaWeb());
			saveProveedor.setReferencia(proveedor.getReferencia());
			saveProveedor.setRfc(proveedor.getRfc());
			saveProveedor.setTelefono(proveedor.getTelefono());
			
			if (proveedor.getIdMunicipio() != null) {
				
				Optional<Municipio> mun = municipioRepository.findById(proveedor.getIdMunicipio());
				
				if (mun.isPresent()) {
					saveProveedor.setMunicipio(mun.get().getNombre());
				}
			}
			
			//set the company(ies)
			saveProveedor.setEmpresas(empresas);
		
			
			//additional attributes for supplier
			saveProveedor.setClaveProv(proveedor.getClaveProv());
			saveProveedor.setSerie(proveedor.getSerie());
			saveProveedor.setMoneda(proveedor.getMoneda());
			
		} catch (Exception e) {
			throw new UnknownError("Ocurrio un error inesperado.");
		}
		
		List<String> nombres = new ArrayList<>(); 
		for (Empresa company: empresas) {nombres.add(company.getNombre());}
		Log.activity("Se agregó un nuevo proveedor: " + proveedor.getClaveProv(), nombres.toString(), "NEW_USER");
		
		return proveedorRepository.save(saveProveedor);
		
	}
	
	public Proveedor updateProveedor(ProveedorDTO proveedor) {
		Proveedor saveProveedor;
		Optional<Proveedor> checkProveedor = proveedorRepository.findById(proveedor.getIdProveedor());
		List<Empresa> empresas = new ArrayList<>();

		if (checkProveedor.isEmpty()) {
			throw new EntityNotFoundException("La proveedor que desea actualizar no existe en el catálogo.");
		} 

		if (proveedor.getRfc().isEmpty() || proveedor.getClaveProv().isEmpty()) {
			throw new NullArgumentException("La clave de proveedor o el RFC no es válido");
		}

		try {
			// assign the supplier
			saveProveedor = checkProveedor.get();
			
			// Transfer the information form the DTO object.
			saveProveedor.setCalle(proveedor.getCalle());
			saveProveedor.setColonia(proveedor.getColonia());
			saveProveedor.setContacto(proveedor.getContacto());
			saveProveedor.setCorreo(proveedor.getCorreo());
			saveProveedor.setCp(proveedor.getCp());
			saveProveedor.setLocalidad(proveedor.getLocalidad());
			saveProveedor.setNombre(proveedor.getNombre());
			saveProveedor.setNumExt(proveedor.getNumExt());
			saveProveedor.setNumInt(proveedor.getNumInt());
			saveProveedor.setPaginaWeb(proveedor.getPaginaWeb());
			saveProveedor.setReferencia(proveedor.getReferencia());
			saveProveedor.setRfc(proveedor.getRfc());
			saveProveedor.setTelefono(proveedor.getTelefono());

			if (proveedor.getIdMunicipio() != null) {
				
				Optional<Municipio> mun = municipioRepository.findById(proveedor.getIdMunicipio());
				if (mun.isPresent()) {
					saveProveedor.setMunicipio(mun.get().getNombre());
				}
				
			}

			if (proveedor.getIdEmpresa() != null) {
				empresas.add(empresaRepository.findById(proveedor.getIdEmpresa()).get());
				saveProveedor.setEmpresas(empresas);
			}

			// additional attributes for supplier
			saveProveedor.setClaveProv(proveedor.getClaveProv());
			saveProveedor.setSerie(proveedor.getSerie());
			saveProveedor.setMoneda(proveedor.getMoneda());
			saveProveedor.setBitActivo(proveedor.getBitActivo());

		} catch (Exception e) {
			throw new UnknownError("Ocurrio un error inesperado.");
		}

		List<String> nombres = new ArrayList<>(); 
		for (Empresa company: empresas) {nombres.add(company.getNombre());}
		Log.activity("Se actualizó un proveedor: " + proveedor.getClaveProv(), nombres.toString(), "UPDATE");
		return proveedorRepository.save(saveProveedor);

	}
	
	public void delete(Long id) throws NotFoundException {
		// check if supplier exists
		if (!exist(id)) {
			throw new NotFoundException("El proveedor que desea eliminar no existe.");	
		}
		
		Proveedor proveedor = proveedorRepository.findById(id).get();
		String clave = proveedor.getClaveProv();
		
		proveedorRepository.deleteById(id);
		
		Log.falla("Se eliminó un proveedor: " + clave, "DELETE");
	}
	

}
