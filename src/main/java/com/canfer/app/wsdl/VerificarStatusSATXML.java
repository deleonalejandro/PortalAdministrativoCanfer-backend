//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.16 at 07:11:58 PM CDT 
//


package com.canfer.app.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pusuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pcontraseña" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pusuario",
    "pcontrase\u00f1a",
    "pXml"
})
@XmlRootElement(name = "VerificarStatusSAT_XML")
public class VerificarStatusSATXML {

    protected String pusuario;
    protected String pcontraseña;
    protected String pXml;

    /**
     * Gets the value of the pusuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPusuario() {
        return pusuario;
    }

    /**
     * Sets the value of the pusuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPusuario(String value) {
        this.pusuario = value;
    }

    /**
     * Gets the value of the pcontraseña property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcontraseña() {
        return pcontraseña;
    }

    /**
     * Sets the value of the pcontraseña property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcontraseña(String value) {
        this.pcontraseña = value;
    }

    /**
     * Gets the value of the pXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPXml() {
        return pXml;
    }

    /**
     * Sets the value of the pXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPXml(String value) {
        this.pXml = value;
    }

}
