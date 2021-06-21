package com.canfer.app.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.apache.commons.lang.NullArgumentException;
import org.aspectj.weaver.tools.cache.CacheKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Municipio;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Sucursal;
import com.canfer.app.dto.EmpresaDTO;
import com.canfer.app.dto.SucursalDTO;
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
	@Autowired
	private RepositoryService superRepo;

	
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
			
			if (empresa.getProfilePictureName() != null) {
				
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
	
	
	/* SUCURSAL METHODS
	 * 
	 * 
	 * 
	 * ****************/
	
	public Sucursal saveSucursal(SucursalDTO sucursal) throws NotFoundException {
		
		Sucursal newSucursal;
		Optional<Sucursal> checkSucursal;
		Empresa empresa;
		Optional<Proveedor> proveedor;
		
		empresa = empresaRepository.findByRfc(sucursal.getEmpresaRfc());
		if (empresa == null) {
			Log.falla("Error al registrar una nueva sucursal, la empresa asociada no existe en el catálogo.", "ERROR");
			throw new NotFoundException("La empresa no existe en el catálogo.");
		}
		
		proveedor = superRepo.findProveedorByEmpresaAndClaveProv(empresa, sucursal.getClaveProv());
		if (proveedor.isEmpty()) {
			Log.falla("Error al registrar una nueva sucursal, el proveedor asociado no existe en el catálogo.", "ERROR");
			throw new NotFoundException("El proveedor no existe en el catálogo.");
		}
		
		checkSucursal = superRepo.findSucursalByEmpresaAndClaveProv(empresa, sucursal.getClaveProv());
		
		if(checkSucursal.isPresent()) {
			throw new EntityExistsException("La sucursal que desea registrar ya existe.");
		} else {
			newSucursal = new Sucursal(empresa, proveedor.get());
		}
		
		return superRepo.save(newSucursal);
		
	}
	
	public Boolean deleteSucursal(Long id) {
		
		Optional<Sucursal> sucursal = superRepo.findSucursalById(id);
		
		if (sucursal.isPresent()) {
			
			String sucursalName = sucursal.get().getNombreSucursal() + " " + sucursal.get().getClaveProv();
			String empresaName = sucursal.get().getEmpresa().getNombre();
			
			superRepo.delete(sucursal.get());
			Log.activity("Se eliminó la sucursal " + sucursalName + " exitosamente." , empresaName, "DELETE");
			
			return true;
		} else {
			
			Log.falla("Error al eliminar la sucursal.", "ERROR_DELETE");
			return false;
		}
	}
	
	public Boolean updateSucursal(SucursalDTO sucursal) {
		
		Sucursal updateSucursal;
		
		Optional<Sucursal> checkSucursal = superRepo.findSucursalById(sucursal.getIdSucursal());
		
		if(checkSucursal.isEmpty()) {
			
			throw new EntityExistsException("La sucursal que desea actualizar no se encuentra en la base de datos.");
			
		} 
		
		updateSucursal = checkSucursal.get();
		
		try {
			
			updateSucursal.setClaveProv(sucursal.getClaveProv());
			updateSucursal.setNombreSucursal(sucursal.getNombreSucursal());
			updateSucursal.updateRfc();

			superRepo.save(updateSucursal);
			
			return true;
			
		} catch (Exception e) {
			
			throw new UnknownError("Ocurrio un error inesperado");
			
		}
		
		
		
	}


}
