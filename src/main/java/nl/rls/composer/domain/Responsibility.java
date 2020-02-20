package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Responsibility extends OwnedEntity {
    @ManyToOne
    private Company responsibleRU;
    @ManyToOne
    private Company responsibleIM;
    public Responsibility(Integer ownerId) {
        super(ownerId);
    }
}
