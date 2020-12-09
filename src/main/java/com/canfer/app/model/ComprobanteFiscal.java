package com.canfer.app.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.ProveedorRepository;
import com.canfer.app.storage.ComprobanteStorageService;
import com.canfer.app.webservice.invoiceone.ValidationService;
import com.canfer.app.webservice.sat.ClientConfigurationSAT;
import com.canfer.app.webservice.sat.SatVerificacionService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo_Comprobante")
public abstract class ComprobanteFiscal {
	
	@Transient
	@Autowired
	private ComprobanteStorageService comprobanteStorageService;
	
	@Transient
	@Autowired
	private ComprobanteFiscalRespository comprobanteRepo;
	
	@Transient
	@Autowired
	private ValidationService validationService; 
	
	@Transient
	@Autowired
	private SatVerificacionService verificationService; 
	
	@Transient
	@Autowired
	private EmpresaRepository empresaRepo;
	
	@Transient
	@Autowired
	private ProveedorRepository proveedorRepo;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idComprobanteFiscal;
	
	@Column(nullable = false)
	@CsvBindByName(column = "Fecha de Carga")
	@CreationTimestamp
	private LocalDateTime fechaCarga;
	
	@CsvBindByName(column = "UUID")
	@Column(nullable = false)
	private String uuid; 
	
	@CsvBindByName(column = "No. SAP")
	@Column(nullable = false)
	private Long idNumSap;
	
	@JoinColumn(name = "idEmpresa")
    @ManyToOne(targetEntity = Empresa.class, fetch = FetchType.LAZY)
    private Empresa empresa;
	
	@JoinColumn(name = "idProveedor")
    @ManyToOne(targetEntity = Proveedor.class, fetch = FetchType.LAZY)
    private Proveedor proveedor;
	 	
	@Column
	@CsvBindByName(column = "Serie")
	private String serie; 

	@Column
	@CsvBindByName(column = "Folio")
	private String folio; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "RFC Empresa")
	private String rfcEmpresa; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "RFC Proveedor")
	private String rfcProveedor; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "Fecha Emisión")
	private String fechaEmision; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "Timbre")
	private String fechaTimbre; 
	
	@Column(nullable = false)
	private String noCertificadoEmpresa; 
	
	@Column(nullable = false)
	private String noCertificadoSat; 
	
	@Column(nullable = false)
	private String versionCfd; 
	
	@Column(nullable = false)
	private String versionTimbre; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "Moneda")
	private String moneda; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "Total")
	private String total; 
	
	@Column(nullable = false)
	@CsvBindByName(column = "Tipo de Documento")
	private String tipoDocumento; 
	
	@Column
	@CsvBindByName(column = "Válido SAT")
	private Boolean bitValidoSAT; 
	
	@Column
	@CsvBindByName(column = "Validación SAT")
	private String respuestaValidacion; 
	
	@Column
	@CsvBindByName(column = "Vigencia")
	private String estatusSAT;
	
	@Column(nullable = false)
	@CsvBindByName(column = "Estatus de Pago")
	private String estatusPago; 
	 
	@Column(nullable = false)
	private Boolean bitRS; 
	
	@Column(nullable = false)
	private Boolean bitRSusuario;
	
	@Column
	@CsvBindByName(column = "Documentos Relacionados")
	private String uuidRelacionados;
	
	@Column
	private String tipoRelacionUuidRelacionados;
	
	@Column
	@CsvBindByName(column = "Comentario")
	private String comentario;
	
	@JoinColumn(name = "idPago")
	@ManyToOne(targetEntity = Pago.class, fetch = FetchType.LAZY)
	private Pago pago;
	
	@JoinColumn(name = "idDocumento")
	@OneToOne(cascade = CascadeType.ALL)
	private Documento documento;
	
	public ComprobanteFiscal(Documento documento, Long consecutivo) {
		
		this.estatusPago = "EN PROCESO";
		this.bitRS = false;
		this.bitRSusuario = false;
		this.comentario = "";
		
		//Set SAP ID
		this.idNumSap = consecutivo;

		this.documento = documento;
	}

	// Concrete Constructor 
	public ComprobanteFiscal(Comprobante comprobante, Empresa empresa, Proveedor proveedor, Long consecutivo) {
		
		this.estatusPago = "EN PROCESO";
		this.bitRS = false;
		this.bitRSusuario = false;
		this.comentario = "";
		
		//Set SAP ID
		this.idNumSap = consecutivo;
		
		//Set the object fields
		this.empresa = empresa;
		this.proveedor = proveedor;
		
		
		//Use the information from the XML to fill the information
		this.uuid = comprobante.getUuidTfd();
		this.serie = comprobante.getSerie();
		this.folio = comprobante.getFolio();
		this.rfcEmpresa = comprobante.getReceptorRfc();
		this.rfcProveedor = comprobante.getEmisorRfc();
		this.fechaEmision = comprobante.getFecha();
		this.fechaTimbre = comprobante.getFechaTimbradoTfd();
		this.noCertificadoEmpresa = comprobante.getNoCertificado();
		this.noCertificadoSat = comprobante.getNoCertificadoSatTfd();
		this.versionCfd = comprobante.getVersion();
		this.versionTimbre = comprobante.getVersionTfd();
		this.moneda = comprobante.getMoneda();
		this.total = comprobante.getTotal();
		this.tipoDocumento = comprobante.getTipoDeComprobante();
		
		//Related UUIDs
		if (comprobante.haveUuidsRelacionados()) {
			this.uuidRelacionados = comprobante.getUuidsRelacionados();
			this.tipoRelacionUuidRelacionados = comprobante.getTipoRelacionUuidRelacionados();
		}
		
	}
	
	public void accept() {
		
		// creamos los nombres y las rutas donde se guardaran los archivos
		this.documento.accept(createName(), createRoute());
			
	}
	
	public void delete() {
		
		this.documento.delete();
		comprobanteRepo.delete(this);
		
	}
	
	public void save() {
		
		this.documento.save();
		comprobanteRepo.save(this);
		
	}
	
	public void fill() {
		
		Comprobante model = this.documento.getArchivoXML().toCfdi();
		Empresa receptor;
		Proveedor emisor;
		List<Proveedor> proveedores;
		
		receptor = empresaRepo.findByRfc(model.getReceptorRfc());
		proveedores = proveedorRepo.findAllByEmpresasAndRfc(receptor, model.getEmisorRfc());
		
		// get the proper provider
		if (proveedores.size() > 1 || proveedores.isEmpty()) {
			// more than one found in the query for PROVEEDOR, use PROVEEDOR GENERICO
			// instead.
			emisor = proveedorRepo.findByEmpresasAndNombre(receptor, "PROVEEDOR GENÉRICO");
		} else {
			emisor = proveedores.get(0);
		}

		// use the proper sequence for the company and module
		consecutivo = consecutivoRepository.findByEmpresaAndModulo(receptor, "Documentos Fiscales");
		idNumSap = consecutivo.getNext();
		consecutivoRepository.save(consecutivo);
				
				
		//Set the object fields
		this.empresa = empresa;
		this.proveedor = proveedor;
		
		
		//Use the information from the XML to fill the information
		this.uuid = model.getUuidTfd();
		this.serie = model.getSerie();
		this.folio = model.getFolio();
		this.rfcEmpresa = model.getReceptorRfc();
		this.rfcProveedor = model.getEmisorRfc();
		this.fechaEmision = model.getFecha();
		this.fechaTimbre = model.getFechaTimbradoTfd();
		this.noCertificadoEmpresa = model.getNoCertificado();
		this.noCertificadoSat = model.getNoCertificadoSatTfd();
		this.versionCfd = model.getVersion();
		this.versionTimbre = model.getVersionTfd();
		this.moneda = model.getMoneda();
		this.total = model.getTotal();
		this.tipoDocumento = model.getTipoDeComprobante();
		
		//Related UUIDs
		if (model.haveUuidsRelacionados()) {
			this.uuidRelacionados = model.getUuidsRelacionados();
			this.tipoRelacionUuidRelacionados = model.getTipoRelacionUuidRelacionados();
		}
		
	}

	public Long getIdComprobanteFiscal() {
		return idComprobanteFiscal;
	}

	public void setIdComprobanteFiscal(Long idComprobanteFiscal) {
		this.idComprobanteFiscal = idComprobanteFiscal;
	}

	public LocalDateTime getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(LocalDateTime fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getIdNumSap() {
		return idNumSap;
	}

	public void setIdNumSap(Long idNumSap) {
		this.idNumSap = idNumSap;
	}

	@JsonIgnore
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@JsonIgnore
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getRfcEmpresa() {
		return rfcEmpresa;
	}

	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}

	public String getRfcProveedor() {
		return rfcProveedor;
	}

	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}
	
	@JsonIgnore
	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getFechaTimbre() {
		return fechaTimbre;
	}

	public void setFechaTimbre(String fechaTimbre) {
		this.fechaTimbre = fechaTimbre;
	}

	public String getNoCertificadoEmpresa() {
		return noCertificadoEmpresa;
	}

	public void setNoCertificadoEmpresa(String noCertificadoEmpresa) {
		this.noCertificadoEmpresa = noCertificadoEmpresa;
	}

	public String getNoCertificadoSat() {
		return noCertificadoSat;
	}

	public void setNoCertificadoSat(String noCertificadoSat) {
		this.noCertificadoSat = noCertificadoSat;
	}

	public String getVersionCfd() {
		return versionCfd;
	}

	public void setVersionCfd(String versionCfd) {
		this.versionCfd = versionCfd;
	}

	public String getVersionTimbre() {
		return versionTimbre;
	}

	public void setVersionTimbre(String versionTimbre) {
		this.versionTimbre = versionTimbre;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getBitValidoSAT() {
		return bitValidoSAT;
	}

	public void setBitValidoSAT(Boolean bitValidoSAT) {
		this.bitValidoSAT = bitValidoSAT;
	}

	public String getRespuestaValidacion() {
		return respuestaValidacion;
	}

	public void setRespuestaValidacion(String respuestaValidacion) {
		this.respuestaValidacion = respuestaValidacion;
	}

	public String getEstatusSAT() {
		return estatusSAT;
	}

	public void setEstatusSAT(String estatusSAT) {
		this.estatusSAT = estatusSAT;
	}

	public String getEstatusPago() {
		return estatusPago;
	}

	public void setEstatusPago(String estatusPago) {
		this.estatusPago = estatusPago;
	}

	public Boolean getBitRS() {
		return bitRS;
	}

	public void setBitRS(Boolean bitRS) {
		this.bitRS = bitRS;
	}

	public Boolean getBitRSusuario() {
		return bitRSusuario;
	}

	public void setBitRSusuario(Boolean bitRSusuario) {
		this.bitRSusuario = bitRSusuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getUuidRelacionados() {
		return uuidRelacionados;
	}

	public void setUuidRelacionados(String uuidRelacionados) {
		this.uuidRelacionados = uuidRelacionados;
	}

	public String getTipoRelacionUuidRelacionados() {
		return tipoRelacionUuidRelacionados;
	}

	public void setTipoRelacionUuidRelacionados(String tipoRelacionUuidRelacionados) {
		this.tipoRelacionUuidRelacionados = tipoRelacionUuidRelacionados;
	}
	
	public void addUuidRelacionados(String uuid) {
		if (this.uuidRelacionados.isEmpty()) {
			this.uuidRelacionados = uuid;
		} else {
			this.uuidRelacionados = this.uuidRelacionados + "," + uuid;
		}
	}
	
	@JsonIgnore
	public List<String> getUuidRelacionadosList(){
		if (this.uuidRelacionados.isEmpty()) {
			return Collections.emptyList();
		}
		return Arrays.asList(this.uuidRelacionados.split(",")); 
	}
	
	public Boolean verificaSat() {
		try {
			
			ClientConfigurationSAT clientconfigurationSAT = new ClientConfigurationSAT();
			SatVerificacionService service = new SatVerificacionService(clientconfigurationSAT);
			String msg = "re=" + this.proveedor.getRfc() + "&" +
						 "rr=" + this.empresa.getRfc() + "&" +
						 "tt=" + this.total + "&" +
						 "id=" + this.uuid;
			
			String respuestaSat =  service.validaVerifica(msg);
			this.setEstatusSAT(respuestaSat);
			comprobanteRepo.save(this);
			
			return true; 
			
		} catch(Exception e) {
			
			Log.general(e.getMessage());
			return false;
		}
		
		
	}
	
	public Boolean validateInvoiceOne() {
			
			List<String> respuestas = validationService.validaVerifica(this.getDocumento().getArchivoXML()); 
			
			// Update information with the responses from validation service.
			this.setBitValidoSAT(Boolean.valueOf(respuestas.get(0)));
			this.setRespuestaValidacion(respuestas.get(1));
			this.setEstatusSAT(respuestas.get(2));

			comprobanteRepo.save(this);
			
			return true; 
		
		
	}
	
	
	// additional methods to access company and supplier
	public String getProveedorClaveProv() {
		return this.proveedor.getClaveProv();
	}
	
	public String getProveedorNombre() {
		return this.proveedor.getNombre();
	}
	
	public Long getProveedorIdProveedor() {
		return this.proveedor.getIdProveedor();
	}
	
	public String getEmpresaNombre() {
		return this.empresa.getNombre();
	}
	
	public String getEmpresaCorreo() {
		return this.empresa.getCorreo();
	}
	
	// additional methods to access payment   
	public Float getPagoTotalParcialidad() {
		if (this.pago != null) { 
			return this.pago.getTotalParcialidad();
		}
		return null;
	}
	
	public Float getPagoTotalPago() {
		if (this.pago != null) {
			return this.pago.getTotalPago(); 
		}
		return null;
	}
	
	public Date getPagoFecmvto() {
		if (this.pago != null) {
			return this.pago.getFecMvto(); 	
		}
		return null;
	}
	
	public Integer getPagoIdNumPago() {
		if (this.pago != null) {
			return this.pago.getIdNumPago(); 
		}
		return null;
	}
	
	public Long getPagoIdPago() {
		if (this.pago != null) {
			return this.pago.getIdPago(); 
		}
		return null;
	}
	
	public Float getPagoTotalFactura() {
		if (this.pago != null) {
			return this.pago.getTotalFactura(); 
		}
		return null;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	private String createName() {

		String folioFinal;

		if (this.folio == null) {

			folioFinal = String.valueOf(this.idNumSap);

		} else {

			folioFinal = this.folio;

		}

		if (this.serie == null) {

			return this.rfcProveedor + "_" + folioFinal;

		} else {

			return this.rfcProveedor + "_" + this.serie + "_" + folioFinal;

		}

	}

	private String createRoute() {
		
		LocalDateTime today = LocalDateTime.now();
		
		Path route = Paths.get(this.rfcEmpresa, 
				String.valueOf(today.getYear()),
				String.valueOf(today.getMonthValue()), 
				this.rfcProveedor);

		return comprobanteStorageService.init(route);
		
	}
	
	
	
	@Entity
	@DiscriminatorValue("FACTURA")
	public static class Factura extends ComprobanteFiscal{
		
		@JoinColumn(name = "idComplemento", nullable= true)
	    @ManyToOne(fetch = FetchType.LAZY)
	    private ComplementoPago complemento;
		
		
		public Factura(Documento documento) {
			super(documento);
		}
		
		public Factura(Comprobante comprobante, Empresa empresa, Proveedor proveedor, Long consecutivo) {
			super(comprobante, empresa, proveedor, consecutivo);
		}
		
		@JsonIgnore
		public ComplementoPago getComplemento() {
			return complemento;
		}
		
		public void setComplemento(ComplementoPago complemento) {
			this.complemento = complemento;
		}
		
		public void removeComplemento() {
			this.complemento = null;
		}
		
		public Long getIdComplemento() {
			if (this.complemento != null) {
				return this.complemento.getIdComprobanteFiscal(); 
			} else {
				return null;
			}
		}
		
		public boolean getHasComplemento() {
			return this.complemento != null;
		}

	}
	
	@Entity
	@DiscriminatorValue("NOTA_DE_CREDITO")
	public static class NotaDeCredito extends ComprobanteFiscal {
		
		public NotaDeCredito(Documento documento) {
			super(documento);
		}

		public NotaDeCredito(Comprobante comprobante, Empresa empresa, Proveedor proveedor, Long consecutivo) {
			super(comprobante, empresa, proveedor, consecutivo);
		}
		
		
	}
	
	@Entity
	@DiscriminatorValue("COMPLEMENTO_DE_PAGO")
	public static class ComplementoPago extends ComprobanteFiscal {
		
		public ComplementoPago(Documento documento) {
			super(documento);
		}
		
		public ComplementoPago(Comprobante comprobante, Empresa empresa, Proveedor proveedor, Long consecutivo) {
			super(comprobante, empresa, proveedor, consecutivo);
		}
		

	}

	
	

}
