package com.canfer.app.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
	
	private String facturas;
	private String entries;
	private String entry;
	private String error;
	private String download;
	private String logo;
	private String crystal;
	private String qr;
	private String payments;
	private String log;

	/**
	 * Folder location for storing files
	 */
	// take values from .properties file
	private Path facturasLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Facturas", "PortalProveedores");
	private Path entriesLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Entradas");
	private Path entryPortalLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Entries");
	private Path errorLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Error");
	private Path downloadLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Descargas");
	private Path logoLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "logos");
	
	public Path getFacturasLocation() {
		return facturasLocation;
	}
	public void setFacturasLocation(Path facturasLocation) {
		this.facturasLocation = facturasLocation;
	}
	public Path getEntriesLocation() {
		return entriesLocation;
	}
	public void setEntriesLocation(Path entriesLocation) {
		this.entriesLocation = entriesLocation;
	}
	public Path getErrorLocation() {
		return errorLocation;
	}
	public void setErrorLocation(Path errorLocation) {
		this.errorLocation = errorLocation;
	}
	public Path getDownloadLocation() {
		return downloadLocation;
	}
	public void setDownloadLocation(Path downloadLocation) {
		this.downloadLocation = downloadLocation;
	}
	public Path getLogoLocation() {
		return logoLocation;
	}
	public void setLogoLocation(Path logoLocation) {
		this.logoLocation = logoLocation;
	}
	public Path getEntryPortalLocation() {
		return entryPortalLocation;
	}
	public void setEntryPortalLocation(Path entryPortalLocation) {
		this.entryPortalLocation = entryPortalLocation;
	}
	public String getFacturas() {
		return facturas;
	}
	public void setFacturas(String facturas) {
		this.facturas = facturas;
	}
	public String getEntries() {
		return entries;
	}
	public void setEntries(String entries) {
		this.entries = entries;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCrystal() {
		return crystal;
	}
	public void setCrystal(String crystal) {
		this.crystal = crystal;
	}
	public String getQr() {
		return qr;
	}
	public void setQr(String qr) {
		this.qr = qr;
	}
	public String getPayments() {
		return payments;
	}
	public void setPayments(String payments) {
		this.payments = payments;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	
	
	
	
	
	
}
