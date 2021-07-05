package com.canfer.app.cfd;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import javax.management.loading.PrivateClassLoader;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "http://www.sat.gob.mx/cfd/3", name = "Comprobante")
public class Comprobante {

	private Emisor emisor;
	private Receptor receptor;
	private Complemento complemento;
	private Conceptos conceptos;
	private CfdiRelacionados cfdiRelacionados;

	private String version;
	private String serie;
	private String folio;
	private String fecha;
	private String sello;
	private String formaPago;
	private String noCertificado;
	private String certificado;
	private String condicionesDePago;
	private String subTotal;
	private String moneda;
	private String tipoCambio;
	private String total;
	private String tipoDeComprobante;
	private String metodoPago;
	private String lugarExpedicion;

	public Comprobante() {
		// default constructor
	}

	public String getVersion() {
		return version;
	}

	public String getSerie() {
		return serie;
	}

	public String getFolio() {
		return folio;
	}

	public String getFecha() {
		return fecha;
	}

	public String getSello() {
		return sello;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public String getNoCertificado() {
		return noCertificado;
	}

	public String getCertificado() {
		return certificado;
	}

	public String getCondicionesDePago() {
		return condicionesDePago;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public String getMoneda() {
		return moneda;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public String getTotal() {
		return total;
	}

	public String getTipoDeComprobante() {
		return tipoDeComprobante;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public String getLugarExpedicion() {
		return lugarExpedicion;
	}

	public Emisor getEmisor() {
		return emisor;
	}

	public Receptor getReceptor() {
		return receptor;
	}

	public Complemento getComplemento() {
		return complemento;
	}

	public Conceptos getConceptos() {
		return conceptos;
	}

	public CfdiRelacionados getCfdiRelacionados() {
		return cfdiRelacionados;
	}

	// getting elements from private classes

	public String getEmisorRfc() {
		return this.emisor.rfc;
	}

	public String getEmisorNombre() {
		return this.emisor.nombre;
	}

	public String getEmisorRegimenFiscal() {
		return this.emisor.regimenFiscal;
	}

	public String getReceptorRfc() {
		return this.receptor.rfc;
	}

	public String getReceptorNombre() {
		return this.receptor.nombre;
	}

	public String getReceptorUsoCFDI() {
		return this.receptor.usoCFDI;
	}

	public String getVersionTfd() {
		return this.complemento.timbreFiscalDigital.version;
	}

	public String getUuidTfd() {
		return this.complemento.timbreFiscalDigital.uuid;
	}

	public String getFechaTimbradoTfd() {
		return this.complemento.timbreFiscalDigital.fechaTimbrado;
	}

	public String getRfcProvCertifTdf() {
		return this.complemento.timbreFiscalDigital.rfcProvCertif;
	}

	public String getSelloCfdTfd() {
		return this.complemento.timbreFiscalDigital.selloCFD;
	}

	public String getNoCertificadoSatTfd() {
		return this.complemento.timbreFiscalDigital.noCertificadoSat;
	}

	public String getSelloSatTfd() {
		return this.complemento.timbreFiscalDigital.selloSAT;
	}

	public boolean haveUuidsRelacionados() {
		return this.cfdiRelacionados != null;
	}

	public String getUuidsRelacionados() {
		// TODO VERIFY IN WHICH CASES CFDI RELACIONADOS
		if (this.cfdiRelacionados.cdfiList.isEmpty()) {
			return "";
		} else {
			StringBuilder sb = new StringBuilder();
			this.cfdiRelacionados.cdfiList.forEach(cfdi -> sb.append(cfdi.uuid + ","));
			return sb.toString();
		}
	}

	public String getTipoRelacionUuidRelacionados() {
		return this.cfdiRelacionados.tipoRelacion;
	}
	
	public List<String> getComplementoRelatedDocsList(){
		
		List<String> uuidList = new ArrayList<>();
		try {
			
			if (this.complemento.pagos == null) {
				return new ArrayList<>();
			} else {
				
				for (Pago pago : this.complemento.pagos.pagoList) {
					for (DoctoRelacionado doc : pago.doctoRelacionados) {
						uuidList.add(doc.idDocumento);
					}
				}
				
				return uuidList;
			}
			
		} catch (Exception e) {
			throw new NullPointerException("No existen pagos para este complemento.");
		}
	}

	@XmlElement(name = "CfdiRelacionados")
	public void setCfdiRelacionados(CfdiRelacionados cfdiRelacionados) {
		this.cfdiRelacionados = cfdiRelacionados;
	}

	@XmlElement(name = "Conceptos")
	public void setConceptos(Conceptos conceptos) {
		this.conceptos = conceptos;
	}

	@XmlAttribute(name = "Version")
	public void setVersion(String version) {
		this.version = version;
	}

	@XmlAttribute(name = "Serie")
	public void setSerie(String serie) {
		this.serie = serie;
	}

	@XmlAttribute(name = "Folio")
	public void setFolio(String folio) {
		this.folio = folio;
	}

	@XmlAttribute(name = "Fecha")
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@XmlAttribute(name = "Sello")
	public void setSello(String sello) {
		this.sello = sello;
	}

	@XmlAttribute(name = "FormaPago")
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	@XmlAttribute(name = "NoCertificado")
	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}

	@XmlAttribute(name = "Certificado")
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	@XmlAttribute(name = "CondicionesDePago")
	public void setCondicionesDePago(String condicionesDePago) {
		this.condicionesDePago = condicionesDePago;
	}

	@XmlAttribute(name = "SubTotal")
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	@XmlAttribute(name = "Moneda")
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@XmlAttribute(name = "TipoCambio")
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	@XmlAttribute(name = "Total")
	public void setTotal(String total) {
		this.total = total;
	}

	@XmlAttribute(name = "TipoDeComprobante")
	public void setTipoDeComprobante(String tipoDeComprobante) {
		this.tipoDeComprobante = tipoDeComprobante;
	}

	@XmlAttribute(name = "MetodoPago")
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	@XmlAttribute(name = "LugarExpedicion")
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	@XmlElement(name = "Emisor")
	public void setEmisor(Emisor emisor) {
		this.emisor = emisor;
	}

	@XmlElement(name = "Receptor")
	public void setReceptor(Receptor receptor) {
		this.receptor = receptor;
	}

	@XmlElement(name = "Complemento")
	public void setComplemento(Complemento complemento) {
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "Comprobante [emisor=" + emisor + ", receptor=" + receptor + ", complemento=" + complemento
				+ ", conceptos=" + conceptos + ", version=" + version + ", serie=" + serie + ", folio=" + folio
				+ ", fecha=" + fecha + ", sello=" + sello + ", formaPago=" + formaPago + ", noCertificado="
				+ noCertificado + ", certificado=" + certificado + ", condicionesDePago=" + condicionesDePago
				+ ", subTotal=" + subTotal + ", moneda=" + moneda + ", tipoCambio=" + tipoCambio + ", total=" + total
				+ ", tipoDeComprobante=" + tipoDeComprobante + ", metodoPago=" + metodoPago + ", lugarExpedicion="
				+ lugarExpedicion + "]";
	}

	@XmlRootElement(name = "Emisor")
	private static class Emisor {

		private String rfc;
		private String nombre;
		private String regimenFiscal;

		public Emisor() {
			// default constructor
		}
		
		public String getRfc() {
			return rfc;
		}

		public String getNombre() {
			return nombre;
		}

		public String getRegimenFiscal() {
			return regimenFiscal;
		}

		@XmlAttribute(name = "Rfc")
		public void setRfc(String rfc) {
			this.rfc = rfc;
		}

		@XmlAttribute(name = "Nombre")
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		@XmlAttribute(name = "RegimenFiscal")
		public void setRegimenFiscal(String regimenFiscal) {
			this.regimenFiscal = regimenFiscal;
		}

		@Override
		public String toString() {
			return "Emisor [rfc=" + rfc + ", nombre=" + nombre + ", regimenFiscal=" + regimenFiscal + "]";
		}

	}
	
	@XmlRootElement(name = "Receptor")
	private static class Receptor {

		private String rfc;
		private String nombre;
		private String usoCFDI;

		public Receptor() {
			// default constructor
		}

		public String getRfc() {
			return rfc;
		}

		public String getNombre() {
			return nombre;
		}

		public String getUsoCFDI() {
			return usoCFDI;
		}

		@XmlAttribute(name = "Rfc")
		public void setRfc(String rfc) {
			this.rfc = rfc;
		}

		@XmlAttribute(name = "Nombre")
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		@XmlAttribute(name = "UsoCFDI")
		public void setUsoCDFI(String usoCFDI) {
			this.usoCFDI = usoCFDI;
		}

		@Override
		public String toString() {
			return "Receptor [rfc=" + rfc + ", nombre=" + nombre + ", usoCFDI=" + usoCFDI + "]";
		}

	}

	@XmlRootElement(name = "Complemento")
	private static class Complemento {

		private TimbreFiscalDigital timbreFiscalDigital;
		private Pagos pagos;

		public Complemento() {
			// default constructor
		}

		public void setTimbreFiscalDigital(TimbreFiscalDigital timbreFiscalDigital) {
			this.timbreFiscalDigital = timbreFiscalDigital;
		}

		@XmlElement(namespace = "http://www.sat.gob.mx/TimbreFiscalDigital", name = "TimbreFiscalDigital")
		public TimbreFiscalDigital getTimbreFiscalDigital() {
			return timbreFiscalDigital;
		}

		@XmlElement(namespace = "http://www.sat.gob.mx/Pagos", name = "Pagos")
		public Pagos getPagos() {
			return pagos;
		}

		public void setPagos(Pagos pagos) {
			this.pagos = pagos;
		}

		@Override
		public String toString() {
			return "Complemento [timbreFiscalDigital=" + timbreFiscalDigital + "]";
		}

	}

	@XmlRootElement(name = "TimbreFiscalDigital")
	private static class TimbreFiscalDigital {

		private String version;
		private String uuid;
		private String fechaTimbrado;
		private String rfcProvCertif;
		private String selloCFD;
		private String noCertificadoSat;
		private String selloSAT;

		public TimbreFiscalDigital() {
			// default constructor 
		}

		public String getVersion() {
			return version;
		}

		public String getUuid() {
			return uuid;
		}

		public String getFechaTimbrado() {
			return fechaTimbrado;
		}

		public String getRfcProvCertif() {
			return rfcProvCertif;
		}

		public String getSelloCFD() {
			return selloCFD;
		}

		public String getNoCertificadoSat() {
			return noCertificadoSat;
		}

		public String getSelloSAT() {
			return selloSAT;
		}

		@XmlAttribute(name = "Version")
		public void setVersion(String version) {
			this.version = version;
		}

		@XmlAttribute(name = "UUID")
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		@XmlAttribute(name = "FechaTimbrado")
		public void setFechaTimbrado(String fechaTimbrado) {
			this.fechaTimbrado = fechaTimbrado;
		}

		@XmlAttribute(name = "RfcProvCertif")
		public void setRfcProvCertif(String rfcProvCertif) {
			this.rfcProvCertif = rfcProvCertif;
		}

		@XmlAttribute(name = "SelloCFD")
		public void setSelloCFD(String selloCFD) {
			this.selloCFD = selloCFD;
		}

		@XmlAttribute(name = "NoCertificadoSAT")
		public void setNoCertificadoSat(String noCertificadoSat) {
			this.noCertificadoSat = noCertificadoSat;
		}

		@XmlAttribute(name = "SelloSAT")
		public void setSelloSAT(String selloSAT) {
			this.selloSAT = selloSAT;
		}

		@Override
		public String toString() {
			return "TimbreFiscalDigital [version=" + version + ", uuid=" + uuid + ", fechaTimbrado=" + fechaTimbrado
					+ ", rfcProvCertif=" + rfcProvCertif + ", selloCFD=" + selloCFD + ", noCertificadoSat="
					+ noCertificadoSat + ", selloSAT=" + selloSAT + "]";
		}

	}

	@XmlRootElement(name = "Conceptos")
	public static class Conceptos {

		private List<Concepto> conceptosList;

		public Conceptos() {
			// default constructor
		}

		public void setConceptosList(List<Concepto> conceptosList) {
			this.conceptosList = conceptosList;
		}

		@XmlElement(name = "Concepto")
		public List<Concepto> getConceptosList() {
			return conceptosList;
		}
		
		public List<String> getClaveProdServList() {
			List<String> claveProdServList = new ArrayList<>();
			for (Concepto concepto : conceptosList) {
				claveProdServList.add(concepto.getClaveProdServ());
			}
			return claveProdServList;
		}
		
		public List<String> getNoIdentificacionList() {
			List<String> noIdentificacionList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				noIdentificacionList.add(concepto.getNoIdentificacion());
			}
			return noIdentificacionList;

		}
		
		public List<String> getCantidadList() {
			List<String> cantidadList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				cantidadList.add(concepto.getCantidad());
			}
			return cantidadList;
		}
		
		public List<String> getClaveDeUnidadList() {
			List<String> claveDeUnidadList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				claveDeUnidadList.add(concepto.getClaveUnidad());
			}
			return claveDeUnidadList;
		}
		
		public List<String> getUnidadList() {
			List<String> unidadList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				unidadList.add(concepto.getUnidad());
			}
			return unidadList;
		}
		
		public List<String> getValorUnitarioList() {
			List<String> valorUnitarioList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				valorUnitarioList.add(concepto.getValorUnitario());
			}
			return valorUnitarioList;
		}
		
		public List<String> getImporteList() {
			List<String> importeList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				importeList.add(concepto.getImporte());
			}
			return importeList;
		}
		
		public List<String> getDescuentoList() {
			List<String> descuentoList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				descuentoList.add(concepto.getDescuento());
			}
			return descuentoList;
		}
		
		public List<String> getNoPedimentoList() {
			List<String> noPedimentoList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				if (concepto.getInfoAduanera() != null) {
					noPedimentoList.add(concepto.getInfoAduanera().getNumeroPedimento());
				} else {
					noPedimentoList.add("-");
				}
			}
			return noPedimentoList;
		}
		
		public List<String> getNoCuentaPredial() {
			List<String> noCuentaPredialList = new ArrayList<>();
			for(Concepto concepto : conceptosList) {
				if (concepto.getCuentaPredial() != null) {
					noCuentaPredialList.add(concepto.getCuentaPredial().getNumero());
				} else {
					noCuentaPredialList.add("-");
				}
			}
			Object[] arr = noCuentaPredialList.toArray();
	
			return noCuentaPredialList;
		}
		

		@Override
		public String toString() {
			return "Conceptos [conceptosList=" + conceptosList + "]";
		}

	}

	@XmlRootElement(name = "Concepto")
	public static class Concepto {

		private InformacionAduanera infoAduanera;
		private CuentaPredial cuentaPredial;
		private String claveProdServ;
		private String noIdentificacion;
		private String cantidad;
		private String claveUnidad;
		private String unidad;
		private String descripcion;
		private String valorUnitario;
		private String importe;
		private String descuento = "-";

		public Concepto() {
			// default constructor
		}

		public String getClaveProdServ() {
			return claveProdServ;
		}

		public String getNoIdentificacion() {
			return noIdentificacion;
		}

		public String getCantidad() {
			return cantidad;
		}

		public String getClaveUnidad() {
			return claveUnidad;
		}

		public String getUnidad() {
			return unidad;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public String getValorUnitario() {
			return valorUnitario;
		}

		public String getImporte() {
			return importe;
		}
		
		public String getDescuento() {
			if (this.descuento.isEmpty()) {
				return "-";
			}
			return descuento;
		}

		@XmlElement(name = "InformacionAduanera")
		public InformacionAduanera getInfoAduanera() {
			return infoAduanera;
		}
		
		@XmlElement(name = "CuentaPredial")
		public CuentaPredial getCuentaPredial() {
			return cuentaPredial;
		}

		public void setCuentaPredial(CuentaPredial cuentaPredial) {
			this.cuentaPredial = cuentaPredial;
		}

		public void setInfoAduanera(InformacionAduanera infoAduanera) {
			this.infoAduanera = infoAduanera;
		}
		
		@XmlAttribute(name = "Descuento")
		public void setDescuento(String descuento) {
			this.descuento = descuento;
		}

		@XmlAttribute(name = "ClaveProdServ")
		public void setClaveProdServ(String claveProdServ) {
			this.claveProdServ = claveProdServ;
		}

		@XmlAttribute(name = "NoIdentificacion")
		public void setNoIdentificacion(String noIdentificacion) {
			this.noIdentificacion = noIdentificacion;
		}

		@XmlAttribute(name = "Cantidad")
		public void setCantidad(String cantidad) {
			this.cantidad = cantidad;
		}

		@XmlAttribute(name = "ClaveUnidad")
		public void setClaveUnidad(String claveUnidad) {
			this.claveUnidad = claveUnidad;
		}

		@XmlAttribute(name = "Unidad")
		public void setUnidad(String unidad) {
			this.unidad = unidad;
		}

		@XmlAttribute(name = "Descripcion")
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		@XmlAttribute(name = "ValorUnitario")
		public void setValorUnitario(String valorUnitario) {
			this.valorUnitario = valorUnitario;
		}

		@XmlAttribute(name = "Importe")
		public void setImporte(String importe) {
			this.importe = importe;
		}

		@Override
		public String toString() {
			return "Concepto [claveProdServ=" + claveProdServ + ", noIdentificacion=" + noIdentificacion + ", cantidad="
					+ cantidad + ", claveUnidad=" + claveUnidad + ", unidad=" + unidad + ", descripcion=" + descripcion
					+ ", valorUnitario=" + valorUnitario + ", importe=" + importe + "]";
		}

	}
	
	@XmlRootElement(name = "InformacionAduanera")
	public static class InformacionAduanera {
		
		private String numeroPedimento = "-";

		public String getNumeroPedimento() {
			if (this.numeroPedimento.isEmpty()) {
				return "-";
			}
			return numeroPedimento;
		}

		@XmlAttribute(name = "NumeroPedimento")
		public void setNumeroPedimento(String numeroPedimento) {
			this.numeroPedimento = numeroPedimento;
		}

		@Override
		public String toString() {
			return "InformacionAduanera [numeroPedimento=" + numeroPedimento + "]";
		}
		
		
	}
	
	@XmlRootElement(name = "CuentaPredial")
	public static class CuentaPredial {
		
		private String numero = "-";

		public String getNumero() {
			if (this.numero.isEmpty()) {
				return "-";
			}
			return numero;
		}

		@XmlAttribute(name = "Numero")
		public void setNumero(String numero) {
			this.numero = numero;
		}

		@Override
		public String toString() {
			return "CuentaPredial [numero=" + numero + "]";
		}
		
		
	}
	

	@XmlRootElement(name = "CfdiRelacionados")
	private static class CfdiRelacionados {

		private String tipoRelacion;
		private List<CfdiRelacionado> cdfiList = new ArrayList<>();

		public CfdiRelacionados() {
			// default constructor
		}

		public void setTipoRelacion(String tipoRelacion) {
			this.tipoRelacion = tipoRelacion;
		}

		public void setCdfiList(List<CfdiRelacionado> cdfiList) {
			this.cdfiList = cdfiList;
		}

		@XmlAttribute(name = "TipoRelacion")
		public String getTipoRelacion() {
			return tipoRelacion;
		}

		@XmlElement(name = "CfdiRelacionado")
		public List<CfdiRelacionado> getCdfiList() {
			return cdfiList;
		}

	}

	@XmlRootElement(name = "CfdiRelacionado")
	private static class CfdiRelacionado {

		private String uuid;

		public CfdiRelacionado() {
			// default constructor
		}

		public String getUuid() {
			return uuid;
		}

		@XmlAttribute(name = "UUID")
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

	}
	
	@XmlRootElement(name = "Pagos")
	private static class Pagos {
		
		private String version;
		private List<Pago> pagoList;
		
		public Pagos() {
			// default constructor
		}

		public String getVersion() {
			return version;
		}

		@XmlAttribute(name = "Version")
		public void setVersion(String version) {
			this.version = version;
		}
		
		@XmlElement(namespace = "http://www.sat.gob.mx/Pagos", name = "Pago")
		public List<Pago> getPagoList() {
			return pagoList;
		}

		public void setPagoList(List<Pago> pagoList) {
			this.pagoList = pagoList;
		}
			
		
	}
	
	private static class Pago {
		
		//attibutos
		private String fechaPago;
		private String formaDePagoP;
		private String monedaP;
		private String monto;
		private String numOperacion;
		private String rfcEmisorCtaOrd;
		private String ctaOrdenante;
		private String rfcEmisorCtaBen;
		private String ctaBeneficiario;
		
		//elementos
		private List<DoctoRelacionado> doctoRelacionados;
		
		public Pago() {
			// default constructor
		}

		public String getFechaPago() {
			return fechaPago;
		}

		@XmlAttribute(name = "FechaPago")
		public void setFechaPago(String fechaPago) {
			this.fechaPago = fechaPago;
		}

		public String getFormaDePagoP() {
			return formaDePagoP;
		}

		@XmlAttribute(name = "FormaDePagoP")
		public void setFormaDePagoP(String formaDePagoP) {
			this.formaDePagoP = formaDePagoP;
		}

		public String getMonedaP() {
			return monedaP;
		}

		@XmlAttribute(name = "MonedaP")
		public void setMonedaP(String monedaP) {
			this.monedaP = monedaP;
		}

		public String getMonto() {
			return monto;
		}

		@XmlAttribute(name = "Monto")
		public void setMonto(String monto) {
			this.monto = monto;
		}

		public String getNumOperacion() {
			return numOperacion;
		}

		@XmlAttribute(name = "NumOperacion")
		public void setNumOperacion(String numOperacion) {
			this.numOperacion = numOperacion;
		}

		public String getRfcEmisorCtaOrd() {
			return rfcEmisorCtaOrd;
		}

		@XmlAttribute(name = "RfcEmisorCtaOrd")
		public void setRfcEmisorCtaOrd(String rfcEmisorCtaOrd) {
			this.rfcEmisorCtaOrd = rfcEmisorCtaOrd;
		}

		public String getCtaOrdenante() {
			return ctaOrdenante;
		}

		@XmlAttribute(name = "CtaOrdenante")
		public void setCtaOrdenante(String ctaOrdenante) {
			this.ctaOrdenante = ctaOrdenante;
		}

		public String getRfcEmisorCtaBen() {
			return rfcEmisorCtaBen;
		}

		@XmlAttribute(name = "RfcEmisorCtaBen") 
		public void setRfcEmisorCtaBen(String rfcEmisorCtaBen) {
			this.rfcEmisorCtaBen = rfcEmisorCtaBen;
		}

		public String getCtaBeneficiario() {
			return ctaBeneficiario;
		}

		@XmlAttribute(name = "CtaBeneficiario")
		public void setCtaBeneficiario(String ctaBeneficiario) {
			this.ctaBeneficiario = ctaBeneficiario;
		}

		@XmlElement(namespace = "http://www.sat.gob.mx/Pagos", name = "DoctoRelacionado")
		public List<DoctoRelacionado> getDoctoRelacionados() {
			return doctoRelacionados;
		}

		public void setDoctoRelacionados(List<DoctoRelacionado> doctoRelacionados) {
			this.doctoRelacionados = doctoRelacionados;
		}
		
		
	}
	
	private static class DoctoRelacionado {
		
		//attributes
		private String idDocumento;
		private String folio;
		private String monedaDR;
		private String metodoDePagoDR;
		private String numParcialidad;
		private String impSaldoAnt;
		private String impPagado;
		private String impSaldoInsoluto;
		
		public DoctoRelacionado() {
			// default constructor
		}

		public String getIdDocumento() {
			return idDocumento;
		}

		@XmlAttribute(name = "IdDocumento")
		public void setIdDocumento(String idDocumento) {
			this.idDocumento = idDocumento;
		}

		public String getFolio() {
			return folio;
		}

		@XmlAttribute(name = "Folio")
		public void setFolio(String folio) {
			this.folio = folio;
		}

		public String getMonedaDR() {
			return monedaDR;
		}

		@XmlAttribute(name = "MonedaDR")
		public void setMonedaDR(String monedaDR) {
			this.monedaDR = monedaDR;
		}

		public String getMetodoDePagoDR() {
			return metodoDePagoDR;
		}

		@XmlAttribute(name = "MetodoDePagoDR")
		public void setMetodoDePagoDR(String metodoDePagoDR) {
			this.metodoDePagoDR = metodoDePagoDR;
		}

		public String getNumParcialidad() {
			return numParcialidad;
		}

		@XmlAttribute(name = "NumParcialidad")
		public void setNumParcialidad(String numParcialidad) {
			this.numParcialidad = numParcialidad;
		}

		public String getImpSaldoAnt() {
			return impSaldoAnt;
		}

		@XmlAttribute(name = "ImpSaldoAnt")
		public void setImpSaldoAnt(String impSaldoAnt) {
			this.impSaldoAnt = impSaldoAnt;
		}

		public String getImpPagado() {
			return impPagado;
		}

		@XmlAttribute(name = "ImpPagado")
		public void setImpPagado(String impPagado) {
			this.impPagado = impPagado;
		}

		public String getImpSaldoInsoluto() {
			return impSaldoInsoluto;
		}

		@XmlAttribute(name = "ImpSaldoInsoluto")
		public void setImpSaldoInsoluto(String impSaldoInsoluto) {
			this.impSaldoInsoluto = impSaldoInsoluto;
		}
		
		
	}

}
