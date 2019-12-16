//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.15 at 11:40:04 PM CET 
//


package nl.rls.ci.soap.dto;

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
 *         &lt;element ref="{}ResponseStatus"/>
 *         &lt;element ref="{}AckIndentifier"/>
 *         &lt;element ref="{}MessageReference"/>
 *         &lt;element ref="{}Sender"/>
 *         &lt;element ref="{}Recipient"/>
 *         &lt;element ref="{}RemoteLIName"/>
 *         &lt;element ref="{}RemoteLIInstanceNumber"/>
 *         &lt;element ref="{}MessageTransportMechanism"/>
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
    "responseStatus",
    "ackIndentifier",
    "messageReference",
    "sender",
    "recipient",
    "remoteLIName",
    "remoteLIInstanceNumber",
    "messageTransportMechanism"
})
@XmlRootElement(name = "LI_TechnicalAck")
public class LITechnicalAck {

    @XmlElement(name = "ResponseStatus", required = true)
    protected String responseStatus;
    @XmlElement(name = "AckIndentifier", required = true)
    protected String ackIndentifier;
    @XmlElement(name = "MessageReference", required = true)
    protected MessageReference messageReference;
    @XmlElement(name = "Sender")
    protected int sender;
    @XmlElement(name = "Recipient")
    protected int recipient;
    @XmlElement(name = "RemoteLIName", required = true)
    protected String remoteLIName;
    @XmlElement(name = "RemoteLIInstanceNumber")
    protected int remoteLIInstanceNumber;
    @XmlElement(name = "MessageTransportMechanism", required = true)
    protected String messageTransportMechanism;

    /**
     * Gets the value of the responseStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the value of the responseStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseStatus(String value) {
        this.responseStatus = value;
    }

    /**
     * Gets the value of the ackIndentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAckIndentifier() {
        return ackIndentifier;
    }

    /**
     * Sets the value of the ackIndentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAckIndentifier(String value) {
        this.ackIndentifier = value;
    }

    /**
     * Gets the value of the messageReference property.
     * 
     * @return
     *     possible object is
     *     {@link MessageReference }
     *     
     */
    public MessageReference getMessageReference() {
        return messageReference;
    }

    /**
     * Sets the value of the messageReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageReference }
     *     
     */
    public void setMessageReference(MessageReference value) {
        this.messageReference = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     */
    public int getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     */
    public void setSender(int value) {
        this.sender = value;
    }

    /**
     * Gets the value of the recipient property.
     * 
     */
    public int getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     */
    public void setRecipient(int value) {
        this.recipient = value;
    }

    /**
     * Gets the value of the remoteLIName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemoteLIName() {
        return remoteLIName;
    }

    /**
     * Sets the value of the remoteLIName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemoteLIName(String value) {
        this.remoteLIName = value;
    }

    /**
     * Gets the value of the remoteLIInstanceNumber property.
     * 
     */
    public int getRemoteLIInstanceNumber() {
        return remoteLIInstanceNumber;
    }

    /**
     * Sets the value of the remoteLIInstanceNumber property.
     * 
     */
    public void setRemoteLIInstanceNumber(int value) {
        this.remoteLIInstanceNumber = value;
    }

    /**
     * Gets the value of the messageTransportMechanism property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageTransportMechanism() {
        return messageTransportMechanism;
    }

    /**
     * Sets the value of the messageTransportMechanism property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageTransportMechanism(String value) {
        this.messageTransportMechanism = value;
    }

}
