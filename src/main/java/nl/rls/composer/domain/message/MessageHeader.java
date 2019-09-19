package nl.rls.composer.domain.message;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.domain.Company;

@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
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

