package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Consecutivo;
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
import com.canfer.app.repository.FacturaRepository;
import com.canfer.app.repository.PagoRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.UsuarioRepository;

@Service
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
	@Autowired
	private ConsecutivoRepository consecutivoRepo;
	@Autowired
	private FacturaRepository facturaRepo;

	// SAVE METHODS

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

	public Empresa save(Empresa empresa) {

		return empresaRepo.save(empresa);

	}

	public Proveedor save(Proveedor proveedor) {

		return proveedorRepo.save(proveedor);

	}

	public Pago save(Pago pago) {

		save(pago.getDocumento());
		return pagoRepo.save(pago);

	}

	public Consecutivo save(Consecutivo consecutivo) {

		return consecutivoRepo.save(consecutivo);

	}

	// MULTIPLE SAVE METHODS

	public void saveAllComprobante(List<ComprobanteFiscal> list) {

		list.forEach((comprobante) -> {
			save(comprobante);
		});

	}

	public void saveAllDocumento(List<Documento> list) {

		list.forEach((documento) -> {
			save(documento);
		});

	}

	public void saveAllArchivo(List<Archivo> list) {

		archivoRepo.saveAll(list);

	}

	public void saveAllUsuario(List<Usuario> list) {

		usuarioRepo.saveAll(list);

	}

	public void saveAllEmpresa(List<Empresa> list) {

		empresaRepo.saveAll(list);

	}

	public void saveAllProveedor(List<Proveedor> list) {

		proveedorRepo.saveAll(list);

	}

	public void saveAllPago(List<Pago> list) {

		list.forEach((pago) -> {
			save(pago);
		});

	}

	// DELETE METHODS

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

	public void delete(Empresa empresa) {

		empresaRepo.delete(empresa);

	}

	public void delete(Proveedor proveedor) {

		proveedorRepo.delete(proveedor);

	}

	public void delete(Pago pago) {

		delete(pago.getDocumento());
		pagoRepo.delete(pago);

	}

	public void delete(Consecutivo consecutivo) {

		consecutivoRepo.delete(consecutivo);
	}

	// MULTIPLE DELETE METHODS

	public void deleteAllComprobante(List<ComprobanteFiscal> list) {

		list.forEach((comprobante) -> {
			delete(comprobante);
		});

	}

	public void deleteAllDocumento(List<Documento> list) {

		list.forEach((documento) -> {
			delete(documento);
		});

	}

	public void deleteAllArchivo(List<Archivo> list) {

		archivoRepo.deleteAll(list);

	}

	public void deleteAllUsuario(List<Usuario> list) {

		usuarioRepo.deleteAll(list);

	}

	public void deleteAllEmpresa(List<Empresa> list) {

		empresaRepo.deleteAll(list);

	}

	public void deleteAllProveedor(List<Proveedor> list) {

		proveedorRepo.deleteAll(list);

	}

	public void deleteAllPago(List<Pago> list) {

		list.forEach((pago) -> {
			delete(pago);
		});

	}

	// FIND By ID METHODS

	public Optional<ComprobanteFiscal> findComprobanteById(Long id) {

		return comprobanteRepo.findById(id);

	}

	public Optional<Documento> findDocumentoById(Long id) {

		return documentoRepo.findById(id);

	}

	public Optional<Archivo> findArchivoById(Long id) {

		return archivoRepo.findById(id);

	}

	public Optional<Usuario> findUsuarioById(Long id) {

		return usuarioRepo.findById(id);

	}

	public Optional<Empresa> findEmpresaById(Long id) {

		return empresaRepo.findById(id);

	}

	public Optional<Proveedor> findProveedorById(Long id) {

		return proveedorRepo.findById(id);

	}

	public Optional<Pago> findPagoById(Long id) {

		return pagoRepo.findById(id);

	}

	public Optional<Consecutivo> findConsecutivoById(Long id) {

		return consecutivoRepo.findById(id);

	}

	// FIND ALL By ID METHODS

	public List<ComprobanteFiscal> findAllComprobanteById(List<Long> ids) {

		return comprobanteRepo.findAllById(ids);

	}

	public List<Documento> findAllDocumentoById(List<Long> ids) {

		return documentoRepo.findAllById(ids);

	}

	public List<Archivo> findArchivoById(List<Long> ids) {

		return archivoRepo.findAllById(ids);

	}

	public List<Usuario> findAllUsuarioById(List<Long> ids) {

		return usuarioRepo.findAllById(ids);

	}

	public List<Empresa> findallEmpresaById(List<Long> ids) {

		return empresaRepo.findAllById(ids);

	}

	public List<Proveedor> findAllProveedorById(List<Long> ids) {

		return proveedorRepo.findAllById(ids);

	}

	public List<Pago> findAllPagoById(List<Long> ids) {

		return pagoRepo.findAllById(ids);

	}

	// Find Methods

	public Empresa findEmpresaByRFC(String rfc) {

		return empresaRepo.findByRfc(rfc);

	}

	public List<Proveedor> findProveedorByEmpresaAndRFC(Empresa empresa, String rfc) {

		return proveedorRepo.findAllByEmpresasAndRfc(empresa, rfc);

	}

	public Proveedor findProveedorByEmpresasAndNombre(Empresa empresa, String nombre) {

		return proveedorRepo.findByEmpresasAndNombre(empresa, nombre);

	}

	public Consecutivo findConsecutivoByEmpresaAndModulo(Empresa empresa, String modulo) {

		return consecutivoRepo.findByEmpresaAndModulo(empresa, modulo);

	}

	public Factura findFacturaByUUID(String uuid) {

		return facturaRepo.findByUuid(uuid);

	}

	public List<Factura> findAllFacturaByComplemento(ComplementoPago complemento) {

		return facturaRepo.findAllByComplemento(complemento);

	}

}
