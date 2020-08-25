package com.canfer.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.model.Documento;
import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.DocumentoRepository;

@Service
public class DocumentoService {

	@Autowired
	DocumentoRepository documentoRepository;
	
	public Documento saveDocumentoFiscal(FacturaNotaComplemento factura, String extension, String modulo, String ruta) {
		Documento documento = new Documento();
		
		documento.setIdTabla(factura.getIdFnc());
		documento.setEmpresa(factura.getEmpresa());
		documento.setConcepto(factura.getTipoDocumento() + "_" + factura.getUuid());
		documento.setExtension(extension);
		documento.setModulo(modulo);
		documento.setRuta(ruta);
		
		//Use methods to create route.
		
		return documentoRepository.save(documento);
	}
}
