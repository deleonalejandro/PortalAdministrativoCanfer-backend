package com.canfer.app.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Empresa;
import com.canfer.app.model.ComprobanteFiscal.Factura;
import com.canfer.app.model.Pago;
import com.canfer.app.model.Proveedor;
import com.canfer.app.repository.ComprobanteFiscalRespository;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.FacturaRepository;
import com.canfer.app.repository.PagoRepository;
import com.canfer.app.security.AuthenticationFacade;
import com.canfer.app.security.UserPrincipal;
import com.canfer.app.service.RepositoryService;

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
	@Autowired
	private PagoRepository avisosRepo;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	private EmpresaRepository empresaRepo;
	@Autowired
	private RepositoryService superRepo;
	
    @GetMapping
    public List<ComprobanteFiscal> filterComprobanteFiscalBy(

			@Join(path = "proveedor", alias = "p")
			@Join(path = "empresa", alias = "e")
			@Or({
				@Spec(path="p.nombre", params="proveedor", spec=Like.class), 
				@Spec(path="p.rfc", params="proveedor", spec=Like.class), 
				@Spec(path="rfcProveedor", params="proveedor", spec=Like.class) 
			})
    		@And({
    			@Spec(path="e.rfc", params= "empresa", spec = Equal.class),
                @Spec(path="fechaEmision", params={"registeredAfter","registeredBefore"}, config = "YYYY-MM-dd", spec=Between.class),
                @Spec(path="fechaCarga", params={"uploadAfter","uploadBefore"}, spec=Between.class),
                @Spec(path="folio", params={"sequenceAfter","sequenceBefore"}, spec=Between.class),
				@Spec(path="total", params= {"totalAfter", "totalBefore"}, spec = Between.class),
				@Spec(path="uuid", params= "uuid", spec = Like.class),
				@Spec(path="idNumSap", params= "idNumSap", spec = Equal.class),
				@Spec(path="estatusPago", params= "estatusPago", spec = EqualIgnoreCase.class),
				@Spec(path="versionCfd", params= "versionCfd", spec = Like.class),
				@Spec(path="serie", params= "serie", spec = Like.class),
				@Spec(path="moneda", params= "moneda", spec = Like.class),
				@Spec(path="p.nombre", params= "generico", spec = Equal.class),
				@Spec(path="bitRS", params= "checkSap", spec = Equal.class)}) Specification<ComprobanteFiscal> comprobanteSpec) {

        return comprobanteFiscalRepo.findAll(comprobanteSpec);
    }
    
    @GetMapping("/facturas")
	public List<Factura> findFacturaBy(
			
			@Join(path = "proveedor", alias = "p")
			@Join(path = "empresa", alias = "e")
			@Or({
				@Spec(path="p.nombre", params="proveedor", spec=Like.class), 
				@Spec(path="p.rfc", params="proveedor", spec=Like.class), 
				@Spec(path="rfcProveedor", params="proveedor", spec=Like.class) 
			})
    		@And({
    			@Spec(path="e.rfc", params= "empresa", spec = Equal.class),
                @Spec(path="fechaEmision", params={"registeredAfter","registeredBefore"}, config = "YYYY-MM-dd", spec=Between.class),
                @Spec(path="fechaCarga", params={"uploadAfter","uploadBefore"}, config = "YYYY-MM-dd", spec=Between.class),
                @Spec(path="folio", params={"sequenceAfter","sequenceBefore"}, spec=Between.class),
				@Spec(path="total", params= {"totalAfter", "totalBefore"}, spec = Between.class),
				@Spec(path="uuid", params= "uuid", spec = Like.class),
				@Spec(path="idNumSap", params= "idNumSap", spec = Equal.class),
				@Spec(path="estatusPago", params= "estatusPago", spec = EqualIgnoreCase.class),
				@Spec(path="versionCfd", params= "versionCfd", spec = Like.class),
				@Spec(path="serie", params= "serie", spec = Like.class),
				@Spec(path="moneda", params= "moneda", spec = Like.class),
				@Spec(path="p.nombre", params= "generico", spec = Equal.class),
				@Spec(path="complemento", params= "hasComplemento", spec = NotNull.class),
				@Spec(path="bitRS", params= "checkSap", spec = Equal.class)}) Specification<Factura> facturaSpecification) {

        return facturaRepo.findAll(facturaSpecification);
    }
  
    @PostMapping(value = "/getVigencia/{id}")
    @ResponseBody
	public String getVigencia(@PathVariable long id) {
	
		Optional<ComprobanteFiscal> comprobante = comprobanteFiscalRepo.findById(id);
		if(comprobante.isPresent()) {
			
			return comprobante.get().verificaSat(); 
			
		}
		
		return null;
		
	}
      
    @GetMapping("/avisos/{rfc}")
  	public List<Pago> findAvisosBy(@PathVariable String rfc){
    	return avisosRepo.findByBitProcesadoAndRfcEmpresa(true, rfc);
    }
    
    @GetMapping("/catalogo")
    public List<Proveedor> getCatalogo(@RequestParam String selectedCompany) {
    		
    	Empresa company = empresaRepo.findByRfc(selectedCompany);
    	
    	return superRepo.findAllProveedorByEmpresa(company);
    	
    }
  			
}