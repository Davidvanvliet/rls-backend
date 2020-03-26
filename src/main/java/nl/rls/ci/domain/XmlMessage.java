package nl.rls.ci.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.OwnedEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class XmlMessage extends OwnedEntity {
    String message;
}
