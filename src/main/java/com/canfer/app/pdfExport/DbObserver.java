package com.canfer.app.pdfExport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.canfer.app.mail.EmailSenderService;
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
	CrystalReportService crService; 
	@Autowired
	EmpresaRepository empresaRepository; 
	@Autowired
	FacturaRepository facturaRepository; 
	
	public void checkPago() {

		//Busca pagos que no hayan sido procesados
		List<Pago> pagos = pagoRepository.findByBitProcesadoAndNuevoEstatusFactura(false,"Pagado");
	
		//Si se especifica, manda por mail el pago
		//Se genera el pdf del aviso de pago
		for(Pago pago: pagos) {

			//TODO poner la contraseña en un lugar seguro
			String exportedFile = crService.exportPDF(pago.getRepBaseDatos(),pago.getIdNumPago(), "sa", "q2y72-m9t9q",pago.getRfcEmpresa(),pago.getIdPago());
			if (exportedFile == null) {
				Log.falla("No se pudo exportar el pago "+ pago.getIdNumPago()+ " a PDF.");
				continue; 
			}
			if(pago.getBitEnviarCorreo()) {
				eSenderService.sendEmailAvisoPago(pago, exportedFile);
			}
			
			//Guardamos el bit procesado en la bd
			pago.setBitProcesado(true);
			pagoRepository.save(pago);
			
			Factura factura = facturaRepository.findByRfcEmpresaAndRfcProveedorAndIdNumSap(pago.getRfcEmpresa(), pago.getRfcProveedor(), pago.getIdNumSap());
			factura.setPago(pago);
			facturaRepository.save(factura);
			
			Empresa empresa = empresaRepository.findByRfc(pago.getRfcEmpresa());
			String nombre = "NA"; 
			if(empresa == null) {
				 nombre = pago.getRfcEmpresa();
			}
			else {
				 nombre = empresa.getNombre();
			}
			
			Log.activity("Se ha agregado el Aviso de Pago Número " + pago.getIdNumPago(), nombre);
			};
	}
}
