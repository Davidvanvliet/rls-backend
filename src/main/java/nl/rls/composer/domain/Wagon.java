package nl.rls.composer.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens
 * Wagon relevant data for the wagons within a running train
 */
@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Wagon extends OwnedEntity {
	public Wagon(Integer ownerId) {super(ownerId);}
	/**
	 * Identifies uniquely the freight wagon by its number
	 */
	@ManyToOne
	private WagonIdent wagonNumberFreight;
	/**
	 * Identifies the position of a wagon within a train.  Sequential number starting with the first wagon at the front of train as N°1.
	 */
    private int wagonTrainPosition;
    /**
     * Actual wagon parameters, dependent on load or damage. This group and its elements are optional (contract defines what IM requires). But if there is dangerous goods in the train, then this group is mandatory.
     */
    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private WagonOperationalData wagonOperationalData;
}
