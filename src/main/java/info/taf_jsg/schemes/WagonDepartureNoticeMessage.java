//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.22 at 09:30:01 PM CEST 
//


package info.taf_jsg.schemes;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://taf-jsg.info/schemes}MessageHeader"/>
 *         &lt;element ref="{http://taf-jsg.info/schemes}RelatedReference" minOccurs="0"/>
 *         &lt;element ref="{http://taf-jsg.info/schemes}WagonInformation"/>
 *         &lt;element ref="{http://taf-jsg.info/schemes}Customers"/>
 *         &lt;element ref="{http://taf-jsg.info/schemes}WagonAtDeparture"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "messageHeader",
    "relatedReference",
    "wagonInformation",
    "customers",
    "wagonAtDeparture"
})
@XmlRootElement(name = "WagonDepartureNoticeMessage")
public class WagonDepartureNoticeMessage {

    @XmlElement(name = "MessageHeader", required = true)
    protected MessageHeader messageHeader;
    @XmlElement(name = "RelatedReference")
    protected RelatedReference relatedReference;
    @XmlElement(name = "WagonInformation", required = true)
    protected WagonInformation wagonInformation;
    @XmlElement(name = "Customers", required = true)
    protected Customers customers;
    @XmlElement(name = "WagonAtDeparture", required = true)
    protected WagonAtDeparture wagonAtDeparture;

    /**
     * Gets the value of the messageHeader property.
     * 
     * @return
     *     possible object is
     *     {@link MessageHeader }
     *     
     */
    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the value of the messageHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageHeader }
     *     
     */
    public void setMessageHeader(MessageHeader value) {
        this.messageHeader = value;
    }

    /**
     * Gets the value of the relatedReference property.
     * 
     * @return
     *     possible object is
     *     {@link RelatedReference }
     *     
     */
    public RelatedReference getRelatedReference() {
        return relatedReference;
    }

    /**
     * Sets the value of the relatedReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelatedReference }
     *     
     */
    public void setRelatedReference(RelatedReference value) {
        this.relatedReference = value;
    }

    /**
     * Gets the value of the wagonInformation property.
     * 
     * @return
     *     possible object is
     *     {@link WagonInformation }
     *     
     */
    public WagonInformation getWagonInformation() {
        return wagonInformation;
    }

    /**
     * Sets the value of the wagonInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link WagonInformation }
     *     
     */
    public void setWagonInformation(WagonInformation value) {
        this.wagonInformation = value;
    }

    /**
     * Gets the value of the customers property.
     * 
     * @return
     *     possible object is
     *     {@link Customers }
     *     
     */
    public Customers getCustomers() {
        return customers;
    }

    /**
     * Sets the value of the customers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customers }
     *     
     */
    public void setCustomers(Customers value) {
        this.customers = value;
    }

    /**
     * Gets the value of the wagonAtDeparture property.
     * 
     * @return
     *     possible object is
     *     {@link WagonAtDeparture }
     *     
     */
    public WagonAtDeparture getWagonAtDeparture() {
        return wagonAtDeparture;
    }

    /**
     * Sets the value of the wagonAtDeparture property.
     * 
     * @param value
     *     allowed object is
     *     {@link WagonAtDeparture }
     *     
     */
    public void setWagonAtDeparture(WagonAtDeparture value) {
        this.wagonAtDeparture = value;
    }

}