package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

import java.util.Date;

@Getter
@Setter
public class GenericMessageDto extends ResourceSupport {
    private int messageStatus;
    private int messageType;
    private String messageTypeVersion;
    private String messageIdentifier;
    private Date messageDateTime;
    private String senderReference;
    private String sender;
    private String recipient;
}

