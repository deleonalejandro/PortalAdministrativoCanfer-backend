package com.canfer.app.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	// take values from .properties file
	private Path facturasLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Facturas", "PortalProveedores");
	private Path entriesLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Entradas");
	private Path entryPortalLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Entries");
	private Path errorLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Error");
	private Path okLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "OK");
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
	public Path getOkLocation() {
		return okLocation;
	}
	public void setOkLocation(Path okLocation) {
		this.okLocation = okLocation;
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
	
	
	
	
	
	
}
