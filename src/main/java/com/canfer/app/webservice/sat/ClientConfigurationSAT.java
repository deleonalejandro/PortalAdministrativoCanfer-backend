package com.canfer.app.webservice.sat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@Primary
public class ClientConfigurationSAT {

  @Bean
  public Jaxb2Marshaller marshallerSAT() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("com.canfer.app.wsdl.sat");
    return marshaller;
  }

  @Bean
  public ClientSAT ClientSAT(Jaxb2Marshaller marshaller) {
    ClientSAT clientSAT = new ClientSAT();
    clientSAT.setDefaultUri("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc");
    clientSAT.setMarshaller(marshaller);
    clientSAT.setUnmarshaller(marshaller);
    return clientSAT;
  }

}
