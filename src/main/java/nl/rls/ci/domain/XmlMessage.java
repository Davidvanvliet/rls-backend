package nl.rls.ci.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.OwnedEntity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class XmlMessage extends OwnedEntity {
     @Lob
	 String message;
}
