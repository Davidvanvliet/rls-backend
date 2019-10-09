package nl.rls.ci.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.domain.OwnedEntity;

/**
 * @author berend.wilkens
 * Domain class Message is the standard infrastucture for sending a message 
 * and recieving a response.
 * 
 * UicRequest can contain any message (tcm, etc.)
 */
@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class CiMessage extends OwnedEntity{
	private Date createDate;
	private Date postDate;
	private boolean posted = false;
	@OneToOne(cascade = CascadeType.ALL)
	private UicHeader uicHeader;
	@OneToOne(cascade = CascadeType.ALL)
	private UicRequest uicRequest;
	@OneToOne(cascade = CascadeType.ALL)
	private UicResponse uicResponse;
}
