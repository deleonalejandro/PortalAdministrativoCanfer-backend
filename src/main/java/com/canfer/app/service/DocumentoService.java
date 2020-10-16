package com.canfer.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Documento;
import com.canfer.app.model.Documento.DocumentoPDF;
import com.canfer.app.model.Documento.DocumentoXML;
import com.canfer.app.repository.DocumentoPDFRepository;
import com.canfer.app.repository.DocumentoRepository;
import com.canfer.app.repository.DocumentoXMLRepository;

@Service
public class DocumentoService {

	@Autowired
	DocumentoRepository documentoRepository;
	@Autowired
	DocumentoXMLRepository documentoXMLRepository;
	@Autowired
	DocumentoPDFRepository documentoPDFRepository;
	
	public Documento save(ComprobanteFiscal comprobante, String extension, String modulo, String ruta) {
		
		if (extension.equalsIgnoreCase("xml")) {
			DocumentoXML doc = new DocumentoXML(comprobante.getIdComprobanteFiscal(), 
					comprobante.getEmpresa(), 
					modulo, 
					comprobante.getTipoDocumento() + "_" + comprobante.getUuid(), 
					extension, 
					ruta); 
			// save document xml 
			return documentoXMLRepository.save(doc);
			
		} else {
			DocumentoPDF doc = new DocumentoPDF(comprobante.getIdComprobanteFiscal(), 
					comprobante.getEmpresa(), 
					modulo, 
					comprobante.getTipoDocumento() + "_" + comprobante.getUuid(), 
					extension, 
					ruta);
			// save document pdf
			return documentoPDFRepository.save(doc);
		}

	}
	
	public void deleteFacturaDocuments(Long id) {
		// delete all the document objects related to the particular idTable
		documentoRepository.deleteAll(documentoRepository.findAllByIdTabla(id));
	}
	
	public List<Documento> findAllByIdTabla(Long id) {
		// find all the documents by idTable 
		return documentoRepository.findAllByIdTabla(id);
	}
	
	public Documento findByIdTablaAndExtension(Long id, String extension) {
		return documentoRepository.findByIdTablaAndExtension(id, extension);
	}

	public List<Documento> findAllByIdTablaAndExtension(Long id, String extension) {
		
		return documentoRepository.findAllByIdTablaAndExtension(id, extension);
	}
	
}
