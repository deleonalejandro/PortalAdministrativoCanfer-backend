package com.canfer.app.webservice.invoiceone;


import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;


@Configuration
public class ClientConfiguration {

  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // this package must match the package in the <generatePackage> specified in
    // pom.xml
    marshaller.setContextPath("com.canfer.app.wsdl.invoiceone");
    return marshaller;
  }

  public Client Client(Jaxb2Marshaller marshaller) {
    Client client = new Client();
    client.setDefaultUri("https://invoiceone.mx/ValidaFiscal/wsValidador.asmx");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }

}