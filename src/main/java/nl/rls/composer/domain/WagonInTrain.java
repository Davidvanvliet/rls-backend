package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.BrakeType;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class WagonInTrain {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	/**
	 * Identifies the position of a wagon within a train.  
	 * Sequential number starting with the first wagon at the front of train as N°1.
	 */
    private int position;
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
    @ManyToOne
	private Wagon wagon;
	@ManyToOne
    private TrainComposition trainComposition;

}
