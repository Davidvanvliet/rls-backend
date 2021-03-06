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
 *         &lt;element ref="{http://taf-jsg.info/schemes}WagonNumberFreight"/>
 *         &lt;element ref="{http://taf-jsg.info/schemes}YardArrival"/>
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
    "wagonNumberFreight",
    "yardArrival"
})
@XmlRootElement(name = "WagonYardArrivalMessage")
public class WagonYardArrivalMessage {

    @XmlElement(name = "MessageHeader", required = true)
    protected MessageHeader messageHeader;
    @XmlElement(name = "WagonNumberFreight", required = true)
    protected String wagonNumberFreight;
    @XmlElement(name = "YardArrival", required = true)
    protected YardArrival yardArrival;

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
     * Gets the value of the wagonNumberFreight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWagonNumberFreight() {
        return wagonNumberFreight;
    }

    /**
     * Sets the value of the wagonNumberFreight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWagonNumberFreight(String value) {
        this.wagonNumberFreight = value;
    }

    /**
     * Gets the value of the yardArrival property.
     * 
     * @return
     *     possible object is
     *     {@link YardArrival }
     *     
     */
    public YardArrival getYardArrival() {
        return yardArrival;
    }

    /**
     * Sets the value of the yardArrival property.
     * 
     * @param value
     *     allowed object is
     *     {@link YardArrival }
     *     
     */
    public void setYardArrival(YardArrival value) {
        this.yardArrival = value;
    }

}
