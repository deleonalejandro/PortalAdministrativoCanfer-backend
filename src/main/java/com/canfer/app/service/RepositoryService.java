package com.canfer.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Archivo;
import com.canfer.app.model.ClasificacionCajaChica;
import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.ComprobanteFiscal.ComplementoPago;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Consecutivo;
import com.canfer.app.model.DetFormularioCajaChica;
import com.canfer.app.model.Documento;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.FormularioCajaChica;
import com.canfer.app.model.Pago;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Sucursal;
import com.canfer.app.model.Usuario;
import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;
import com.canfer.app.repository.ArchivoRepository;
import com.canfer.app.repository.ClasificacionCCRepository;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.ConsecutivoRepository;
import com.canfer.app.repository.DetFormularioCCRepository;
import com.canfer.app.repository.DocumentoRepository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.FacturaRepository;
import com.canfer.app.repository.FormularioCajaChicaRepository;
import com.canfer.app.repository.PagoRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.repository.SucursalRepository;
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
	@Autowired 
	private DetFormularioCCRepository detFormCCRepo;
	@Autowired
	private FormularioCajaChicaRepository formCCRepo;
	@Autowired
	private ClasificacionCCRepository clasificacionCCRepo;
	@Autowired
	private SucursalRepository sucursalRepo;

	// SAVE METHODS

	public ComprobanteFiscal save(ComprobanteFiscal comprobante) {

		return comprobanteRepo.save(comprobante);

	}

	public Documento save(Documento documento) {

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

		return pagoRepo.save(pago);

	}

	public Consecutivo save(Consecutivo consecutivo) {

		return consecutivoRepo.save(consecutivo);

	}
	
	public DetFormularioCajaChica save(DetFormularioCajaChica dfcc) {
		
		return detFormCCRepo.save(dfcc);
		
	}
	
	public ClasificacionCajaChica save(ClasificacionCajaChica ccc) {
		
		return clasificacionCCRepo.save(ccc);
	}
	
	public FormularioCajaChica save(FormularioCajaChica formularioCajaChica) {
		
		return formCCRepo.save(formularioCajaChica);
	}
	
	public Sucursal save(Sucursal sucursal) {
		
		return sucursalRepo.save(sucursal);
	}

	// MULTIPLE SAVE METHODS

	public void saveAllComprobante(List<ComprobanteFiscal> list) {

		comprobanteRepo.saveAll(list);

	}

	public void saveAllDocumento(List<Documento> list) {

		documentoRepo.saveAll(list);

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

		pagoRepo.saveAll(list);

	}
	
	public void saveAllFactura(List<Factura> list) {
		
		facturaRepo.saveAll(list);
	}
	
	public void saveAllDetFormularioCC(List<DetFormularioCajaChica> list) {
		
		detFormCCRepo.saveAll(list);
	}

	public void saveAllFormularioCC(List<FormularioCajaChica> list) {
		
		formCCRepo.saveAll(list);
	}
	
	public void saveAllClasificacionCC(List<ClasificacionCajaChica> list) {
		
		clasificacionCCRepo.saveAll(list);
	}
	
	public void saveAllSucursales(List<Sucursal> list) {
		
		sucursalRepo.saveAll(list);
		
	}
	
	
	
	// DELETE METHODS

	public void delete(ComprobanteFiscal comprobante) {

		comprobanteRepo.delete(comprobante);

	}

	public void delete(Documento documento) {

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

		pagoRepo.delete(pago);

	}

	public void delete(Consecutivo consecutivo) {

		consecutivoRepo.delete(consecutivo);
	}
	
	public void delete(DetFormularioCajaChica detFormularioCajaChica) {
		
		detFormCCRepo.delete(detFormularioCajaChica);
	}
	
	public void delete(FormularioCajaChica formularioCajaChica) {
		
		formCCRepo.delete(formularioCajaChica);
	}

	public void delete(ClasificacionCajaChica clasificacionCajaChica) {
		
		clasificacionCCRepo.delete(clasificacionCajaChica);
	}
	
	public void delete(Sucursal sucursal) {
		
		sucursalRepo.delete(sucursal);
	}
	
	// MULTIPLE DELETE METHODS

	public void deleteAllComprobante(List<ComprobanteFiscal> list) {

		comprobanteRepo.deleteAll(list);

	}

	public void deleteAllDocumento(List<Documento> list) {

		documentoRepo.deleteAll(list);

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

		pagoRepo.deleteAll(list);

	}
	
	public void deleteAllDetFormularioCC(List<DetFormularioCajaChica> list) {
		
		detFormCCRepo.deleteAll(list);
	}
	
	public void deleteAllFormularioCC(List<FormularioCajaChica> list) {
		
		formCCRepo.deleteAll(list);
	}
	
	public void deleteAllClasificacionCC(List<ClasificacionCajaChica> list) {
		
		clasificacionCCRepo.deleteAll(list);
	}
	
	public void deleteAllSucursal(List<Sucursal> sucursals) {
		
		sucursalRepo.saveAll(sucursals);
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
	
	public Optional<DetFormularioCajaChica> findDetFormularioCCById(Long id) {
		
		return detFormCCRepo.findById(id);
		
	}
	
	public Optional<FormularioCajaChica> findFormularioCCById(Long id) {
		
		return formCCRepo.findById(id);
	}
	
	public Optional<ClasificacionCajaChica> findClasificacionCCById(Long id) {
		
		return clasificacionCCRepo.findById(id);
	}
	
	public Optional<Sucursal> findSucursalById(Long id) {
		
		return sucursalRepo.findById(id);
	}
	
	// FIND ALL
	
	public List<ClasificacionCajaChica> findAllClasificacionCajaChicas() {
		
		return clasificacionCCRepo.findAll();
	}
	
	public List<Sucursal> findAllSucursales() {
		
		return sucursalRepo.findAll();
		
	}

	// FIND ALL By ID METHODS

	public List<Archivo> findAllArchivoById(List<Long> ids) {
	
		return archivoRepo.findAllById(ids);
	
	}

	public List<ComprobanteFiscal> findAllComprobanteById(List<Long> ids) {

		return comprobanteRepo.findAllById(ids);

	}

	public List<Documento> findAllDocumentoById(List<Long> ids) {

		return documentoRepo.findAllById(ids);

	}

	public List<Usuario> findAllUsuarioById(List<Long> ids) {

		return usuarioRepo.findAllById(ids);

	}

	public List<Empresa> findAllEmpresaById(List<Long> ids) {

		return empresaRepo.findAllById(ids);

	}

	public List<Proveedor> findAllProveedorById(List<Long> ids) {

		return proveedorRepo.findAllById(ids);

	}

	public List<Pago> findAllPagoById(List<Long> ids) {

		return pagoRepo.findAllById(ids);

	}

	public List<ClasificacionCajaChica> findAllClasificacionCCById(List<Long> ids) {
		
		return clasificacionCCRepo.findAllById(ids);
	}
	// Find Methods

	public Empresa findEmpresaByRFC(String rfc) {

		return empresaRepo.findByRfc(rfc);

	}
	
	public List<Proveedor> findAllProveedorByEmpresa(Empresa empresa) {

		return proveedorRepo.findAllByEmpresas(empresa);

	}

	public List<Proveedor> findAllProveedorByEmpresaAndRFC(Empresa empresa, String rfc) {

		return proveedorRepo.findAllByEmpresasAndRfc(empresa, rfc);

	}
	
	public List<Proveedor> findAllProveedorByRfcAndBitActivo(String rfc, Boolean activo) {

		return proveedorRepo.findAllByRfcAndBitActivo(rfc, activo);

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

	public ComprobanteFiscal findComprobanteByUUID(String uuid) {

		return comprobanteRepo.findByUuid(uuid);

	}
	
	public ComprobanteFiscal findComprobanteByDocumento(Documento doc) {
		
		return comprobanteRepo.findByDocumento(doc);
	}

	public Optional<Proveedor> findProveedorByEmpresaAndClaveProv(Empresa empresa, String clave) {
		
		return proveedorRepo.findByEmpresasAndClaveProv(empresa, clave);
	}

	public List<ComprobanteFiscal> findAllComprobanteByRfcEmpresaAndProveedor(String rfc, Proveedor proveedor) {

		return comprobanteRepo.findAllByRfcEmpresaAndProveedor(rfc, proveedor);
	}
	
	public Usuario findUsuarioByUsername(String username) {
		
		return usuarioRepo.findByUsername(username);
	}

	public ComprobanteFiscal findFacturaByRfcEmpresaAndRfcProveedorAndIdNumSap(String rfcEmpresa, String rfcProveedor,
			Long idNumSap) {
		
		return facturaRepo.findByRfcEmpresaAndRfcProveedorAndIdNumSap(rfcEmpresa, 
				rfcProveedor, idNumSap);
	}

	public ComprobanteFiscal findFacturaByPago(Pago pago) {
		return facturaRepo.findByPago(pago);
	}


	public Proveedor findOneProveedorByRFC(String rfcProveedor) {
		List<Proveedor> proveedores = proveedorRepo.findAllByRfc(rfcProveedor);
		return proveedores.get(0);
		
		
	}
	
	public Optional<Documento> findDocumentoByArchivoXML(ArchivoXML archivoXML) {
		
		return documentoRepo.findByArchivoXML(archivoXML);
	}
	
	public Optional<Documento> findDocumentoByArchivoPDF(ArchivoPDF archivoPDF) {
		
		return documentoRepo.findByArchivoPDF(archivoPDF);
	}
	
	public Consecutivo findConsecutivoBySucursal(Sucursal sucursal) {
		
		return consecutivoRepo.findBySucursal(sucursal);
	}
	
	public Optional<DetFormularioCajaChica> findDetFormularioCCByDocumento(Documento documento) {
		
		return detFormCCRepo.findByDocumento(documento);
	}
	
	public List<DetFormularioCajaChica> findAllDetFormularioCCByDocumento(Documento documento) {
		
		return detFormCCRepo.findAllByDocumento(documento);
	}
	
	public List<DetFormularioCajaChica> findAllDetFormularioCajaChicaByFormCC(FormularioCajaChica formularioCajaChica){
		
		return detFormCCRepo.findAllByFormularioCajaChica(formularioCajaChica);
	}
	
	public List<Sucursal> findAllSucursalByUsuario(UsuarioCanfer usuarioCanfer) {
		
		return sucursalRepo.findAllByUsuariosCanfer(usuarioCanfer); 
	}
	
	public List<Sucursal> findAllSucursalByEmpresa(Empresa empresa) {
		
		return sucursalRepo.findAllByEmpresa(empresa);
	}
	
	public List<Sucursal> findAllSucursalByEmpresaIn(List<Empresa> empresas) {
		
		return sucursalRepo.findAllByEmpresaIn(empresas);
	}
	
	public List<FormularioCajaChica> findAllFormularioCajaChicaBySucursal(Sucursal sucursal) {
		
		return formCCRepo.findAllBySucursal(sucursal);
	}
}
