package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * The response of the Web service is an acknowledgement XML enclosed in a SOAP
 * envelope. The acknowledgement xml in the response contains the response
 * status information about the message, and sender and receiver information.
 * The schema for the acknowledgement xml and a sample acknowledgement response
 * message is annexed to this document as annex 4 and 5. The elements of the
 * acknowledgement are described below.
 */
@NoArgsConstructor
@Getter
@Setter
public class UicResponseDto extends RepresentationModel {
    /**
     * ResponseStatus: The value of this field is either ACK or NACK meaning
     * positive and negative acknowledgment respectively. A positive acknowledgement
     * means the partner CI has received the message and accepted it for processing.
     * A negative acknowledgment means, the partner CI has received the message but
     * rejected it as the CI is not partner configured to receive message from the
     * sender using Web Service.
     */
    private String responseStatus;
    /**
     * AckIndentifier: This is a unique Identifier for the acknowledgement. In the
     * reference implementation for the CI it is generated by prefixing ACKID to the
     * message Id from received Message.
     */
    private String ackIndentifier;
    private MessageReferenceDto messageReference;
    /**
     * : Id of the sender company found in the TAF-TSI Message header
     */
    private String sender;
    /**
     * Recipient: Id of the receiver company found in the TAF-TSI Message header 
     * RemoteLIName: The Name of the remote CI as configured in reference
     * implementation for the CI, max 50 characters CI. :
     */
    private String recipient;
    /**
     * The instance Id of the Remote CI as configured the reference implementation
     * for the CI, max 2 numericCI.
     */
    private String RemoteLIInstanceNumber;
    /**
     * The transport mechanism which is always WEBSERVICE in this case.
     */
    private String messageTransportMechanism;

}

