package com.canfer.app.storage;

import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String facturasLocation = "upload-dir";
	private String entriesLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Entradas").toString();
	private String errorLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Error").toString();
	private String okLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "OK").toString();
	private String downloadLocation = Paths.get(System.getProperty("user.home"), "PortalProveedores", "Descargas").toString();
	
	
	
	public String getOkLocation() {
		return okLocation;
	}
	public void setOkLocation(String okLocation) {
		this.okLocation = okLocation;
	}
	public String getFacturasLocation() {
		return facturasLocation;
	}
	public void setFacturasLocation(String facturasLocation) {
		this.facturasLocation = facturasLocation;
	}
	public String getEntriesLocation() {
		return entriesLocation;
	}
	public void setEntriesLocation(String entriesLocation) {
		this.entriesLocation = entriesLocation;
	}
	public String getErrorLocation() {
		return errorLocation;
	}
	public void setErrorLocation(String errorLocation) {
		this.errorLocation = errorLocation;
	}
	public String getDownloadLocation() {
		return downloadLocation;
	}
	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}
	
}
