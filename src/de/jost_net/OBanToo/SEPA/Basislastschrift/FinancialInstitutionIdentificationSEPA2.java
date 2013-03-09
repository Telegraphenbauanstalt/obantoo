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
 * Java class for FinancialInstitutionIdentificationSEPA2 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="FinancialInstitutionIdentificationSEPA2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pain.008.002.02}RestrictedFinancialIdentificationSEPA"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialInstitutionIdentificationSEPA2", propOrder = { "othr" })
public class FinancialInstitutionIdentificationSEPA2
{

  @XmlElement(name = "Othr", required = true)
  protected RestrictedFinancialIdentificationSEPA othr;

  /**
   * Gets the value of the othr property.
   * 
   * @return possible object is {@link RestrictedFinancialIdentificationSEPA }
   * 
   */
  public RestrictedFinancialIdentificationSEPA getOthr()
  {
    return othr;
  }

  /**
   * Sets the value of the othr property.
   * 
   * @param value
   *          allowed object is {@link RestrictedFinancialIdentificationSEPA }
   * 
   */
  public void setOthr(RestrictedFinancialIdentificationSEPA value)
  {
    this.othr = value;
  }

}
