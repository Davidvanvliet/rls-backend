package nl.rls.composer.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens
 * Defines the make up of a train for each section of its journey
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class TrainCompositionJourneySection extends OwnedEntity {
	public TrainCompositionJourneySection(Integer ownerId) {
		super(ownerId);
	}
	@ManyToOne
    private JourneySection journeySection;
	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="train_running_data_id") 
	private TrainRunningData trainRunningData;
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "train_composition_journey_section_id")
    private List<Locomotive> locomotives = new ArrayList<Locomotive>();
    /**
     * Indicates that livestock and people (other than train crew) will be carried. 
     * Coding: if live animals or people are transported = 1, in opposite case = 0. 
     * If code = 1, then at the wagon level for at least one wagon Info- Goods Shape, 
     * Type and Danger has to include the code '98' or Restrictions due to Load or 
     * Damage has to include code '09.'
     */
    private int livestockOrPeopleIndicator;
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "train_composition_journey_section_id")
    private List<Wagon> wagons  = new ArrayList<Wagon>();
}
