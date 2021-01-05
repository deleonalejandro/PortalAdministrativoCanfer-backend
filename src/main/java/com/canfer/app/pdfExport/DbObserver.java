package com.canfer.app.pdfExport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.model.Archivo.ArchivoPDF;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.Log;
import com.canfer.app.model.Pago;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.FacturaRepository;
import com.canfer.app.repository.PagoRepository;

@Service
public class DbObserver {

	@Autowired
	PagoRepository pagoRepository; 
	@Autowired
	EmailSenderService eSenderService;
	@Autowired
	EmpresaRepository empresaRepository; 
	@Autowired
	FacturaRepository facturaRepository; 
	@Autowired 
	CrystalReportService crService; 
	
	public void checkPago() {

		String user = "sa"; 
		String psswd = "q2y72-m9t9q"; 
		
		//Busca pagos que no hayan sido procesados
		List<Pago> pagos = pagoRepository.findByBitProcesadoAndNuevoEstatusFactura(false,"Pagado");
	
		//Si se especifica, manda por mail el pago
		//Se genera el pdf del aviso de pago
		for(Pago pago: pagos) {

			//TODO poner la contraseña en un lugar seguro
			ArchivoPDF exportedFile = crService.exportPDF(pago, user, psswd);
			
			//Guardamos el bit procesado en la bd
			pago.setBitProcesado(true);
			pagoRepository.save(pago);
			
			Factura factura = facturaRepository.findByRfcEmpresaAndRfcProveedorAndIdNumSap(pago.getRfcEmpresa(), 
					pago.getRfcProveedor(), pago.getIdNumSap());
			
			factura.setPago(pago);
			factura.setEstatusPago(pago.getNuevoEstatusFactura().toUpperCase());
			facturaRepository.save(factura);
			
			Empresa empresa = empresaRepository.findByRfc(pago.getRfcEmpresa());
			String nombre = "NA"; 
			
			if(pago.getBitEnviarCorreo()) {
				
				eSenderService.sendEmailAvisoPago(pago);
			}
			
			if(empresa == null) {
				
				 nombre = pago.getRfcEmpresa();
			}
			
			else {
				
				 nombre = empresa.getNombre();
			}
			
			Log.activity("Se ha agregado el Aviso de Pago Número " + pago.getIdNumPago(), nombre, "PAYMENT");
			
			}
	}
}
