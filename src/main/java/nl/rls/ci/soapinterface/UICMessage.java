package nl.rls.ci.soapinterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UICMessage complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UICMessage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="signature" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="senderAlias" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="encoding" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UICMessage", propOrder = {
        "message",
        "signature",
        "senderAlias",
        "encoding"
})
public class UICMessage {

    protected String message;
    protected String signature;
    protected String senderAlias;
    protected String encoding;

    /**
     * Gets the value of the message property.
     *
     * @return possible object is
     * {@link Object }
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the signature property.
     *
     * @return possible object is
     * {@link Object }
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    public void setSignature(String value) {
        this.signature = value;
    }

    /**
     * Gets the value of the senderAlias property.
     *
     * @return possible object is
     * {@link Object }
     */
    public String getSenderAlias() {
        return senderAlias;
    }

    /**
     * Sets the value of the senderAlias property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    public void setSenderAlias(String value) {
        this.senderAlias = value;
    }

    /**
     * Gets the value of the encoding property.
     *
     * @return possible object is
     * {@link Object }
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }

}
