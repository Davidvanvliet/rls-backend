package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TractionInTrain {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	/**
	 * 0 - no driver present in Loco, 1 - driver(s) is /are) present in Loco
	 */
	protected Integer driverIndication;
	/**
	 * Identifies position of intermediate traction unit(s) in the train indicating
	 * after which wagon (specified by order number) the unit is placed.
	 */
	private Integer tractionPositionInTrain;
    @ManyToOne
    private Traction traction;
    @ManyToOne
    private TrainCompositionJourneySection trainCompositionJourneySection;

}
