package com.canfer.app;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
public class ClientConfiguration {

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("com.canfer.app.wsdl");
    return marshaller;
  }

  @Bean
  public Client countryClient(Jaxb2Marshaller marshaller) {
    Client client = new Client();
    client.setDefaultUri("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }

}