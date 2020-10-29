package com.canfer.app.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Pago;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.FacturaRepository;
import com.canfer.app.repository.PagoRepository;

import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.NotNull;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/proveedorApi")
public class ProveedorRestController {


	@Autowired
	private ComprobanteFiscalRespository comprobanteFiscalRepo;
	@Autowired
	private PagoRepository avisosRepo;
	
    @GetMapping
    public List<ComprobanteFiscal> filterComprobanteFiscalBy(

			@Join(path = "proveedor", alias = "p")
			@Join(path = "empresa", alias = "e")
    		@And({
    			@Spec(path="e.rfc", params= "empresa", spec = Equal.class),
    			@Spec(path="p.claveProv", params= "proveedor", spec = Equal.class),
                @Spec(path="fechaEmision", params={"registeredAfter","registeredBefore"}, config = "YYYY-MM-dd", spec=Between.class),
                @Spec(path="folio", params={"sequenceAfter","sequenceBefore"}, spec=Between.class),
				@Spec(path="estatusPago", params= "estatusPago", spec = EqualIgnoreCase.class),
				@Spec(path="serie", params= "serie", spec = Like.class)}) Specification<ComprobanteFiscal> comprobanteSpec) {

        return comprobanteFiscalRepo.findAll(comprobanteSpec);
    }
    
    @GetMapping("/avisos/{rfc}/{clave}")
  	public List<Pago> findAvisosBy(@PathVariable String rfc, @PathVariable String clave){
    	return avisosRepo.findByBitProcesadoAndRfcProveedorAndClaveProveedor(true, rfc, clave);
    }
  
   
}
