package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter
@Setter
public class GenericMessageDto extends RepresentationModel {
    private int messageStatus;
    private int messageType;
    private String messageTypeVersion;
    private String messageIdentifier;
    private Date messageDateTime;
    private String senderReference;
    private String sender;
    private String recipient;
}

