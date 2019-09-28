package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.BrakeType;

/**
 * @author berend.wilkens
 * Wagon relevant data for the wagons within a running train
 */
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
     * Actual wagon parameters, dependent on load or damage. This group and its elements are optional (contract defines what IM requires). But if there is dangerous goods in the train, then this group is mandatory.
     */
	private BrakeType brakeType;
	private int totalLoadWeight;
	private int brakeWeight;
	private int wagonMaxSpeed;
//	@ManyToOne	
//	private ExceptionalGaugingProfile exceptionalGaugingProfile;
//	@ManyToOne
//	private ExceptionalGaugingIdent exceptionalGaugingIdent;
//	@OneToMany
//	private List<DangerousGoodsDetails> dangerousGoodsDetails;
//	@OneToMany
//	private List<InfoOnGoodsShapeTypeDanger> infoOnGoodsShapeTypeDanger;
//	@OneToMany
//	private List<RestrictionCodes> restrictionsDueToLoadOrDamage;

}
