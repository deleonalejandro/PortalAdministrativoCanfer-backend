package com.canfer.app.mail;


import java.io.File;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.canfer.app.model.ComprobanteFiscal;
import com.canfer.app.model.Log;
import com.canfer.app.model.Pago;
import com.canfer.app.model.Proveedor;
import com.canfer.app.model.Usuario.UsuarioCanfer;
import com.canfer.app.model.Usuario.UsuarioProveedor;
import com.canfer.app.repository.EmpresaRepository;
import com.canfer.app.repository.UsuarioCanferRepository;
import com.canfer.app.service.RepositoryService;

/**
 * 
 * Servicio para redactar y enviar mails
 * 
 * @author Alejandro de Leon y Yasmin Femerling
 * @date 09/08/2020
 */

@Service
public class EmailSenderService {
	
	// ==============
	// PRIVATE FIELDS
	// ==============

	@Autowired
	private UsuarioCanferRepository usuarioCanferRep; 
	@Autowired
	private EmpresaRepository empresaRep; 
	@Autowired
	private RepositoryService superRepo; 
    @Autowired
    private TemplateEngine htmlTemplateEngine;
    @Autowired
    private EmailSenderProperties emailSenderProperties;
    
	// ==============
	// PUBLIC METHODS
	// ==============
	
	
	/**
	   * Metodo para escribir un mail y enviarlo
	   * 
	   * @return void
	   */
	
	
	public void sendEmailAvisoPago(Pago pago){

		//Obtener correo de contadores y de proveedor
		List<UsuarioCanfer> contadores = usuarioCanferRep.findAllByEmpresas(
				empresaRep.findByRfc(pago.getRfcEmpresa()));
		
		String to = pago.getCorreo();
		for(UsuarioCanfer contador:contadores) {
			
			to=to+","+contador.getCorreo();
			
		}
		
	    MimeMessage message = emailSenderProperties.createMimeMessage();
	    
	    try {
	    	
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        helper.setTo(InternetAddress.parse(to));
	        helper.setSubject("Aviso de Pago");
	        helper.setFrom(emailSenderProperties.getUsername());
	        helper.setText("Se ha realizado el pago de una factura.");
	        helper.addAttachment("AvisoDePago.pdf", new File(pago.getDocumento().getArchivoPDF().getRuta()));
	        emailSenderProperties.send(message);
	        
	    } catch (MessagingException | MailException e) {

	        Log.falla("No se pudo enviar correo a " + to + " con un aviso de pago.", "ERROR_CONNECTION");;
	    }
	}

	public void sendEmailNewDoc(ComprobanteFiscal comprobante){
		final String EMAIL_TEMPLATE_NAME = "emailNewDoc.html";
        
		//Obtener correo de contadores y de proveedor
		Optional<Proveedor> proveedor = superRepo.findProveedorByEmpresaAndClaveProv(comprobante.getEmpresa(), comprobante.getProveedorClaveProv());
	      
		
		String to = proveedor.get().getCorreo();
		List<UsuarioCanfer> contadores = usuarioCanferRep.findAllByEmpresas(
				empresaRep.findByRfc(comprobante.getRfcEmpresa()));
		for(UsuarioCanfer contador:contadores) {to=to+","+contador.getCorreo();}
		
	    try {
	        
	        // Prepare the evaluation context
	        final Context ctx = new Context();
			ctx.setVariable("result", "El documento fiscal con UUID: "+ comprobante.getUuid()+" fue registrado exitosamente.");
			ctx.setVariable("validez", "Se obtuvo la siguiente respuesta por parte del SAT: "+ comprobante.getRespuestaValidacion()+"" );
			ctx.setVariable("vigencia","El estatus actual del documento es:  "+comprobante.getEstatusPago()+"."); 
			ctx.setVariable("empresa",comprobante.getEmpresaNombre()); 

	        // Prepare message using a Spring helper
	        final MimeMessage mimeMessage = emailSenderProperties.createMimeMessage();
	        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

	        // Create the HTML body using Thymeleaf
	        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_TEMPLATE_NAME, ctx);
	        message.setText(htmlContent, true /* isHtml */);
	        //message.setTo(InternetAddress.parse(to));
	        message.setTo(InternetAddress.parse("a01039359@itesm.mx"));
	        message.setFrom(emailSenderProperties.getUsername());
	        message.setSubject("Recepción de Documento Fiscal.");
			
		   
	        emailSenderProperties.send(mimeMessage);
	        
	    } catch (MessagingException e) {

	    	Log.activity("No se pudo enviar correo a " + to + " con la confirmación de recepción de documento fiscal: "  
	        		+ comprobante.getUuid() + ".", comprobante.getEmpresaNombre() ,"ERROR_CONNECTION");
	    }
	}
	
	public void sendEmailUpdateDoc(ComprobanteFiscal comprobante){
		final String EMAIL_TEMPLATE_NAME = "emailUpdateDoc.html";
        
		//Obtener correo de contadores y de proveedor
		Optional<Proveedor> proveedor = superRepo.findProveedorByEmpresaAndClaveProv(comprobante.getEmpresa(), comprobante.getProveedorClaveProv());
	      
		
		String to = proveedor.get().getCorreo();
		List<UsuarioCanfer> contadores = usuarioCanferRep.findAllByEmpresas(
				empresaRep.findByRfc(comprobante.getRfcEmpresa()));
		for(UsuarioCanfer contador:contadores) {to=to+","+contador.getCorreo();}
		
	    try {
	        
	        // Prepare the evaluation context
	        final Context ctx = new Context();
			ctx.setVariable("result", "El documento fiscal con UUID: "+ comprobante.getUuid()+" fue modificado.");
			ctx.setVariable("validez", "Se obtuvo la siguiente respuesta por parte del SAT: "+ comprobante.getEstatusSAT()+"" );
			ctx.setVariable("vigencia","El estatus actual del documento es:  "+comprobante.getEstatusPago()+"."); 
			ctx.setVariable("comentarios","Comentarios:  "+comprobante.getComentario()+".");
			ctx.setVariable("empresa",comprobante.getEmpresaNombre()); 

	        // Prepare message using a Spring helper
	        final MimeMessage mimeMessage = emailSenderProperties.createMimeMessage();
	        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

	        // Create the HTML body using Thymeleaf
	        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_TEMPLATE_NAME, ctx);
	        message.setText(htmlContent, true /* isHtml */);
	        message.setTo(InternetAddress.parse(to));
	        message.setFrom(emailSenderProperties.getUsername());
	        message.setSubject("Actualización de Documento Fiscal.");
		    
	        emailSenderProperties.send(mimeMessage);
	        
	    } catch (MessagingException | MailException e) {

	        Log.falla("No se pudo enviar correo a " + to + " con la actualización del documento fiscal: "  
	        		+ comprobante.getUuid() + "." ,"ERROR_CONNECTION");
	    }
	}
	
	public void sendEmailNewAccount(UsuarioProveedor usuario, String pass){
		final String EMAIL_TEMPLATE_NAME = "emailUsuarioProv.html";
        
		
	    try {
	        
	        // Prepare the evaluation context
	        final Context ctx = new Context();
			ctx.setVariable("usuario", "Nombre de usuario: "+ usuario.getUsername());
			ctx.setVariable("nombre", "¡Bienvenido "+ usuario.getNombre()+ " " + usuario.getApellido()+"!");
			ctx.setVariable("psss", "Contraseña: "+ pass);

	        // Prepare message using a Spring helper
	        final MimeMessage mimeMessage = emailSenderProperties.createMimeMessage();
	        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");

	        // Create the HTML body using Thymeleaf
	        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_TEMPLATE_NAME, ctx);
	        message.setText(htmlContent, true /* isHtml */);
	        message.setTo(InternetAddress.parse(usuario.getCorreo()));
	        message.setFrom(emailSenderProperties.getUsername());
	        message.setSubject("Nueva Cuenta en Portal de Proveedores.");
		    
	        emailSenderProperties.send(mimeMessage);
	        
	    } catch (MessagingException | MailException e) {

	        Log.falla("No se pudo enviar correo a " + usuario.getCorreo() + " con la información del nuevo usuario proveedor: "  
	        		+ usuario.getUsername() + "." ,"ERROR_CONNECTION");
	    }
	}
	
	
}
