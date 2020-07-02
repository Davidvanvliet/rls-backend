package nl.rls.ci.domain;

import lombok.ToString;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ResponseStatus",
        "AckIndentifier",
        "MessageReference",
        "Sender",
        "Recipient",
        "RemoteLIName",
        "RemoteLIInstanceNumber",
        "MessageTransportMechanism"
})
@XmlRootElement(name = "LI_TechnicalAck")
@ToString
public class LiTechnicalAck {
    @XmlElement(name = "ResponseStatus", required = true)
    private String ResponseStatus;
    @XmlElement(name = "AckIndentifier", required = true)
    private String AckIndentifier;
    @XmlElement(name = "MessageReference")
    private MessageReference MessageReference;
    @XmlElement(name = "Sender")
    private String Sender;
    @XmlElement(name = "Recipient")
    private String Recipient;
    @XmlElement(name = "RemoteLIName", required = true)
    private String RemoteLIName;
    @XmlElement(name = "RemoteLIInstanceNumber")
    private String RemoteLIInstanceNumber;
    @XmlElement(name = "MessageTransportMechanism", required = true)
    private String MessageTransportMechanism;

    public LiTechnicalAck() {
    }

    public String getAckIndentifier() {
        return AckIndentifier;
    }

    public void setAckIndentifier(String ackIndentifier) {
        AckIndentifier = ackIndentifier;
    }

    public MessageReference getMessageReference() {
        return MessageReference;
    }

    public void setMessageReference(MessageReference messageReference) {
        MessageReference = messageReference;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }

    public String getRemoteLIName() {
        return RemoteLIName;
    }

    public void setRemoteLIName(String remoteLIName) {
        RemoteLIName = remoteLIName;
    }

    public String getRemoteLIInstanceNumber() {
        return RemoteLIInstanceNumber;
    }

    public void setRemoteLIInstanceNumber(String remoteLIInstanceNumber) {
        RemoteLIInstanceNumber = remoteLIInstanceNumber;
    }

    public String getMessageTransportMechanism() {
        return MessageTransportMechanism;
    }

    public void setMessageTransportMechanism(String messageTransportMechanism) {
        MessageTransportMechanism = messageTransportMechanism;
    }

    public String getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(String ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }
}
