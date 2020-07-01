package nl.rls.composer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class CustomMessageStatus {
    @Id
    @GeneratedValue
    private int id;
    private boolean acknowledged;
    private String sentBy;
    private String sentToRemoteLi;
    private Date ackKnowledgeMessageDateTime;
    private String messageIdentifier;
    @OneToOne
    private ErrorMessage errorMessage;
    @ManyToOne
    private Train train;

    public CustomMessageStatus() {
    }

    public CustomMessageStatus(boolean acknowledged, String sender, String remoteLIName, Date messageDateTime, String messageIdentifier) {
        this.acknowledged = acknowledged;
        this.sentBy = sender;
        this.sentToRemoteLi = remoteLIName;
        this.ackKnowledgeMessageDateTime = messageDateTime;
        this.messageIdentifier = messageIdentifier;
    }
}
