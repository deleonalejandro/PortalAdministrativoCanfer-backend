package com.canfer.app.pdfExport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.mail.EmailSenderService;
import com.canfer.app.model.Pago;
import com.canfer.app.repository.PagoRepository;

@Service
public class DbObserver {

	@Autowired
	PagoRepository pagoRepository; 
	@Autowired
	EmailSenderService eSenderService;
	@Autowired
	CrystalReportService crService; 
	
	public void checkPago() {
		
		//Busca pagos que no hayan sido procesados
		List<Pago> pagos = pagoRepository.findByBitProcesadoAndNuevoEstatusFactura(false,"Pagado");
	
		//Si se especifica, manda por mail el pago
		//Se genera el pdf del aviso de pago
		pagos.forEach((pago) -> {
			if(pago.getBitEnviarCorreo()) {
				
				String exportedFile = crService.exportPDF(pago.getRepBaseDatos(),pago.getIdNumPago(), "sa", "q2y72-m9t9q");
			
				eSenderService.sendEmail(pago.getCorreo(), "Aviso de Pago", "Le presentamos el Aviso de Pago de su Factura.", exportedFile);
		        
				//Guardamos el bit procesado en la bd
				pago.setBitProcesado(true);
				pagoRepository.save(pago);
			}
		});
 
	}
}
