//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.16 at 07:11:58 PM CDT 
//


package com.canfer.app.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="pUUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pRFCEmisor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pRFCReceptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pTotalDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "puuid",
    "prfcEmisor",
    "prfcReceptor",
    "pTotalDocumento"
})
@XmlRootElement(name = "VerificarStatusSAT")
public class VerificarStatusSAT {

    protected String pusuario;
    protected String pcontraseña;
    @XmlElement(name = "pUUID")
    protected String puuid;
    @XmlElement(name = "pRFCEmisor")
    protected String prfcEmisor;
    @XmlElement(name = "pRFCReceptor")
    protected String prfcReceptor;
    protected String pTotalDocumento;

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
     * Gets the value of the puuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUUID() {
        return puuid;
    }

    /**
     * Sets the value of the puuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUUID(String value) {
        this.puuid = value;
    }

    /**
     * Gets the value of the prfcEmisor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRFCEmisor() {
        return prfcEmisor;
    }

    /**
     * Sets the value of the prfcEmisor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRFCEmisor(String value) {
        this.prfcEmisor = value;
    }

    /**
     * Gets the value of the prfcReceptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRFCReceptor() {
        return prfcReceptor;
    }

    /**
     * Sets the value of the prfcReceptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRFCReceptor(String value) {
        this.prfcReceptor = value;
    }

    /**
     * Gets the value of the pTotalDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPTotalDocumento() {
        return pTotalDocumento;
    }

    /**
     * Sets the value of the pTotalDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPTotalDocumento(String value) {
        this.pTotalDocumento = value;
    }

}
