package nl.rls.ci.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CustomMessageStatusDto {
    private int id;
    private boolean acknowledged;
    private String sentBy;
    private String sentToRemoteLi;
    private Date ackKnowledgeMessageDateTime;
    private String messageIdentifier;
    private ErrorMessageDto errorMessage;
}
