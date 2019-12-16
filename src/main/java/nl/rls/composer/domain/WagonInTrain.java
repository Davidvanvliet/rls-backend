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
public class WagonInTrain {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	/**
	 * Identifies the position of a wagon within a train.  
	 * Sequential number starting with the first wagon at the front of train as N°1.
	 */
    private int position;
    @ManyToOne
    private WagonLoad wagonLoad;
    @ManyToOne
    private TrainCompositionJourneySection trainCompositionJourneySection;

}
