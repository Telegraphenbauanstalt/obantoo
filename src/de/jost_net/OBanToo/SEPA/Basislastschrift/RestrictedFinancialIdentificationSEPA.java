//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.04 at 09:57:14 AM CET 
//

package de.jost_net.OBanToo.SEPA.Basislastschrift;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RestrictedFinancialIdentificationSEPA complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictedFinancialIdentificationSEPA">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.002.02}RestrictedSMNDACode"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedFinancialIdentificationSEPA", propOrder = { "id" })
public class RestrictedFinancialIdentificationSEPA
{

  @XmlElement(name = "Id", required = true)
  protected RestrictedSMNDACode id;

  /**
   * Gets the value of the id property.
   * 
   * @return possible object is {@link RestrictedSMNDACode }
   * 
   */
  public RestrictedSMNDACode getId()
  {
    return id;
  }

  /**
   * Sets the value of the id property.
   * 
   * @param value
   *          allowed object is {@link RestrictedSMNDACode }
   * 
   */
  public void setId(RestrictedSMNDACode value)
  {
    this.id = value;
  }

}