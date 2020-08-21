package com.canfer.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.canfer.app.cfd.Comprobante;
import com.canfer.app.cfd.XmlService;
import com.canfer.app.model.Documento;
import com.canfer.app.model.FacturaNotaComplemento;
import com.canfer.app.repository.DocumentoRepository;
import com.canfer.app.storage.FacturaStorageService;
import com.canfer.app.webService.ValidationService;

import javassist.NotFoundException;

@Service
public class CargaDocumentoService {

	@Autowired
	private FacturaStorageService facturaStorageService;
	@Autowired
	private XmlService xmlService;
	@Autowired
	private FacturaNotaComplementoService facturaNotaComplementoService;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private ValidationService validationService;

	public FacturaNotaComplemento saveXML(InputStream file, String modulo) throws IOException {

		File tempFile;
		Comprobante comprobante;
		Documento documentoXML;
		LocalDateTime today = LocalDateTime.now();
		Path xmlPathFilename;
		Path foldersPath;
		String extensionXML = "xml";
		String rutaXML;
		String concepto;
		List<String> respuestasValidacion;
		
		
	    //Use temp file to extract XML information.  
		tempFile = File.createTempFile("tempXML", ".xml");
		tempFile.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(tempFile);
		IOUtils.copy(file, fos);
		fos.close();

		try {

			// Fetch XML information.
			comprobante = xmlService.xmlToObject(tempFile);
			// Verify if the invoice exists.
			if (facturaNotaComplementoService.exist(comprobante.getComplemento().getTimbreFiscalDigital().getUuid())) {
				throw new FileAlreadyExistsException("La factura ya ha sido registrada.");
			}
			
			//Create folders path
			foldersPath = Paths.get(comprobante.getReceptor().getRfc(), String.valueOf(today.getYear()),
					String.valueOf(today.getMonthValue()), comprobante.getEmisor().getRfc());
			

			// After invoice duplication verification, save the document in the system
			// Path creation for document XML
			if (comprobante.getSerie() == null) {
				xmlPathFilename = Paths.get(foldersPath.toString(),comprobante.getEmisor().getRfc() + "_" + comprobante.getFolio() + "."
						+ extensionXML );
			} else {
				xmlPathFilename = Paths.get(foldersPath.toString(), comprobante.getEmisor().getRfc() + "_" + comprobante.getSerie() + "_" + comprobante.getFolio() + "."
								+ extensionXML);
			}
			
			//We extract the information again from tmp file.
			InputStream is = new FileInputStream(tempFile);
			
			// Save the document in disk
			facturaStorageService.store(is, xmlPathFilename);
			// Get the route of the file
			rutaXML = facturaStorageService.load(xmlPathFilename.toString()).toString();

			/*
			 * PAC VERIFICATION CALL
			 */
			respuestasValidacion = validationService.validaVerifica(rutaXML);
			
			//Concept assignation 
			concepto = comprobante.getTipoDeComprobante() + "_" + comprobante.getComplemento().getTimbreFiscalDigital().getUuid();
			
			// Document object creation for the XML document.
			documentoXML = new Documento(modulo, concepto, extensionXML, rutaXML);

			/*
			 * Persist all entities into the DB. Invoice, XML Document, PDF Document
			 */
			
			documentoXML = documentoRepository.saveAndFlush(documentoXML);
		
			return facturaNotaComplementoService.createAndSave(comprobante, documentoXML, respuestasValidacion);


		} catch (JAXBException | IOException | NotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public void savePDF(InputStream is, FacturaNotaComplemento factura) {

		String extensionPDF = "pdf";
		LocalDateTime today = LocalDateTime.now();
		Path pdfPathFilename;
		String rutaPDF;
		Documento documentoPDF;
		
		//Check if factura exists.
		if (factura == null) {
			throw new ResourceAccessException("La factura ya fue registrada. Añadir el pdf correspondiente en el detalle.");
		}
		
		Documento documentoXML = factura.getXmlDocumento();
		String rutaXML = documentoXML.getRuta();

		String[] subString = StringUtils.substringsBetween(rutaXML, "Facturas" + File.separator + "PortalProveedores" + File.separator, "xml");
		//Create PDF path
		pdfPathFilename = Paths.get(subString[0] + extensionPDF);
				
		// Save the PDF document on disk
		facturaStorageService.store(is, pdfPathFilename);
		
		// Get the route of the file
		rutaPDF = facturaStorageService.load(pdfPathFilename.toString()).toString();
		
		// Document object creation for the PDF document.
		documentoPDF = new Documento(documentoXML.getModulo(), documentoXML.getConcepto(), extensionPDF, rutaPDF);
		
		//Save and update entities. 
		documentoRepository.saveAndFlush(documentoPDF);
		
		//Add the new fields to the invoice object.
		factura.setPdfDate(today);
		//Set the PDF document to the invoice.
		factura.setPdfDocumento(documentoPDF);
		
		//Save (update) the invoice
		facturaNotaComplementoService.save(factura);
		
	}
	

}
