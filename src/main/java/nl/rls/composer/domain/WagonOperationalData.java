package nl.rls.composer.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.domain.code.BrakeType;

/**
 * @author berend.wilkens
 * 
 *         Actual wagon parameters, dependent on load or damage. This group and
 *         its elements are optional (contract defines what IM requires). But if
 *         there is dangerous goods in the train, then this group is mandatory.
 */
@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class WagonOperationalData extends OwnedEntity {
	/**
	 * Type of braking system
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
	public WagonOperationalData(BrakeType brakeType, int totalLoadWeight) {
		super();
		this.brakeType = brakeType;
		this.totalLoadWeight = totalLoadWeight;
	}
}
