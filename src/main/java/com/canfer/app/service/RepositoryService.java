package com.canfer.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Documento;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Pago;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario;
import com.canfer.app.repository.ArchivoRepository;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.DocumentoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.PagoRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.UsuarioRepository;

public class RepositoryService {
	
	@Autowired 
	private ComprobanteFiscalRespository comprobanteRepo; 
	@Autowired 
	private DocumentoRepository documentoRepo; 
	@Autowired
	private ArchivoRepository archivoRepo; 
	@Autowired
	private UsuarioRepository usuarioRepo; 
	@Autowired
	private EmpresaRepository empresaRepo; 
	@Autowired
	private ProveedorRepository proveedorRepo; 
	@Autowired
	private PagoRepository pagoRepo; 
	
	
	//SAVE METHODS
	
	public ComprobanteFiscal save(ComprobanteFiscal comprobante) {
		
		save(comprobante.getDocumento());
		return comprobanteRepo.save(comprobante);
		
	}
	
	public Documento save(Documento documento) {
		
		if (documento.hasPDF()) {
			
			save(documento.getArchivoPDF());
		
		}
		
		if (documento.hasXML()) {
			
			save(documento.getArchivoXML());
		
		}
		
		return documentoRepo.save(documento);
		
	}
	
	public Archivo save(Archivo archivo) {
		
		return archivoRepo.save(archivo);
		
	}
	
	public Usuario save(Usuario usuario) {
		
		return usuarioRepo.save(usuario);
		
	}
	
	public  Empresa save(Empresa empresa) {
		
		return empresaRepo.save(empresa);
		
	}
	
	public Proveedor save(Proveedor proveedor) {
		
		return proveedorRepo.save(proveedor);
		
	}
	
	public Pago save(Pago pago) {
		
		save(pago.getDocumento());
		return pagoRepo.save(pago);
		
	}
	
	//DELETE METHODS
	
	public void delete(ComprobanteFiscal comprobante) {
		
		delete(comprobante.getDocumento());
		comprobanteRepo.delete(comprobante);
		
	}
	
	public void delete(Documento documento) {
		
		if (documento.hasPDF()) {
			
			delete(documento.getArchivoPDF());
		
		}
		
		if (documento.hasXML()) {
			
			delete(documento.getArchivoXML());
		
		}
		
		 documentoRepo.delete(documento);
		
	}
	
	public void delete(Archivo archivo) {
		
		 archivoRepo.delete(archivo);
		
	}
	
	public void delete(Usuario usuario) {
		
		 usuarioRepo.delete(usuario);
		
	}
	
	public  void delete(Empresa empresa) {
		
		 empresaRepo.delete(empresa);
		
	}
	
	public void delete(Proveedor proveedor) {
		
		 proveedorRepo.delete(proveedor);
		
	}
	
	public void delete(Pago pago) {
		
		delete(pago.getDocumento());
		pagoRepo.delete(pago);
		
	}
	
	//MULTIPLE DELETE METHODS
	
		public <T> void delete(List<T> list) {
			
			if (list.get(0) instanceof ComprobanteFiscal) {
				
				List<ComprobanteFiscal> comprobantes = (List<ComprobanteFiscal>) list; 
				comprobantes.forEach((comprobante) -> {
		           delete(comprobante);
		        });
				
			} else if (list.get(0) instanceof Documento) {
				
				List<Documento> documentos = (List<Documento>) list;
				documentos.forEach((comprobante) -> {
			           delete(documentos);
			    });
				
				
			} else if (list.get(0) instanceof Archivo) {
				
				List<Archivo> archivos = (List<Archivo>) list;
				archivoRepo.deleteAll(archivos);
				
				
			} else if (list.get(0) instanceof Usuario) {
				
				List<Usuario> usuarios = (List<Usuario>) list;
				usuarioRepo.deleteAll(usuarios);
				
			} else if (list.get(0) instanceof Empresa) {
				
				List<Empresa> empresas = (List<Empresa>) list;
				empresaRepo.deleteAll(empresas);
				
				
			} else if (list.get(0) instanceof Proveedor) {
				
				List<Proveedor> proveedores = (List<Proveedor>) list;
				proveedorRepo.deleteAll(proveedores);
				
				
			} else if (list.get(0) instanceof Pago) {
				
				List<Pago> pagos = (List<Pago>) list;
				pagos.forEach((pago) -> {
			           delete(pago);
			        });
				
				
			} 
			
		}
	
	
	
}
