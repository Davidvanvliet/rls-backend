package nl.rls.composer.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.domain.Company;

import javax.persistence.*;

@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class MessageHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ownerId;
    @OneToOne
    private MessageReference messageReference;
    /**
     * Additional information used to route the message to the correct recieving application (if needed)
     */
//    private int messageRoutingID;
    /**
     * reference used by the sender (e.g.  FTP file name)
     */
    private String senderReference;
    @ManyToOne
    private Company sender;
    @ManyToOne
    private Company recipient;
}

