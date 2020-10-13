package com.canfer.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.FacturaRepository;

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
@RequestMapping(value = "/documentosFiscalesApi")
public class FNCRestController {
	
	@Autowired
	private ComprobanteFiscalRespository comprobanteFiscalRepo;
	@Autowired
	private FacturaRepository facturaRepo;
	
    @GetMapping
    public List<ComprobanteFiscal> filterComprobanteFiscalBy(

			@Join(path = "proveedor", alias = "p")
			@Or({
				@Spec(path="p.nombre", params="proveedor", spec=Like.class), 
				@Spec(path="p.rfc", params="proveedor", spec=Like.class) 
			})
    		@And({
                @Spec(path="fechaEmision", params={"registeredAfter","registeredBefore"}, config = "YYYY-MM-dd", spec=Between.class),
                @Spec(path="folio", params={"sequenceAfter","sequenceBefore"}, spec=Between.class),
				@Spec(path="total", params= {"totalAfter", "totalBefore"}, spec = Between.class),
				@Spec(path="uuid", params= "uuid", spec = Like.class),
				@Spec(path="idNumSap", params= "idNumSap", spec = Like.class),
				@Spec(path="estatusPago", params= "estatusPago", spec = EqualIgnoreCase.class),
				@Spec(path="versionCfd", params= "versionCfd", spec = Like.class),
				@Spec(path="serie", params= "serie", spec = Like.class),
				@Spec(path="moneda", params= "moneda", spec = Like.class),
				@Spec(path="bitRS", params= "checkSap", spec = Equal.class)}) Specification<ComprobanteFiscal> comprobanteSpec) {

        return comprobanteFiscalRepo.findAll(comprobanteSpec);
    }
    
    @GetMapping("/facturas")
	public List<Factura> findFacturaBy(
			
			@Join(path = "proveedor", alias = "p")
			@Or({
				@Spec(path="p.nombre", params="proveedor", spec=Like.class), 
				@Spec(path="p.rfc", params="proveedor", spec=Like.class) 
			})
    		@And({
                @Spec(path="fechaEmision", params={"registeredAfter","registeredBefore"}, config = "YYYY-MM-dd", spec=Between.class),
                @Spec(path="folio", params={"sequenceAfter","sequenceBefore"}, spec=Between.class),
				@Spec(path="total", params= {"totalAfter", "totalBefore"}, spec = Between.class),
				@Spec(path="uuid", params= "uuid", spec = Like.class),
				@Spec(path="idNumSap", params= "idNumSap", spec = Like.class),
				@Spec(path="estatusPago", params= "estatusPago", spec = EqualIgnoreCase.class),
				@Spec(path="versionCfd", params= "versionCfd", spec = Like.class),
				@Spec(path="serie", params= "serie", spec = Like.class),
				@Spec(path="moneda", params= "moneda", spec = Like.class),
				@Spec(path="complemento", params= "hasComplemento", spec = NotNull.class),
				@Spec(path="bitRS", params= "checkSap", spec = Equal.class)}) Specification<Factura> facturaSpec) {

        return facturaRepo.findAll(facturaSpec);
    }
    		

	
}