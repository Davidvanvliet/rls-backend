package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Responsibility extends OwnedEntity {
	public Responsibility(Integer ownerId) {super(ownerId);}
	@ManyToOne
    private Company responsibleRU;
	@ManyToOne
    private Company responsibleIM;
}
