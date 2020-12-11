package com.canfer.app.model;

import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.Archivo.ArchivoXML;

public interface IModuleEntity {
	
	public Documento fetchDocument();
	
	public ArchivoXML fetchXML();
	
	public ArchivoPDF fetchPDF();

}
