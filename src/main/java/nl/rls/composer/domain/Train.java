package nl.rls.composer.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.domain.message.TrainCompositionMessage;

@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class Train extends OwnedEntity {
    /**
     * Identifies the train for traffic management purposes by the Dispatcher, GSMR services, etc.
     */
    private String operationalTrainNumber;
    /**
     * The scheduled departure date and time or the scheduled handover date and time at the border between two different IMs.
     */
    private Date scheduledTimeAtHandover;
    /**
     * Scheduled Date and Time at a location related to the status of the train or wagon at the given location.
     */
    private Date scheduledDateTimeAtTransfer;

	@ManyToOne
	private Location transferPoint;
	/**
	 * Next IM
	 */
	@ManyToOne
	private Company transfereeIM;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_id")
	private List<TrainCompositionJourneySection> trainCompositionJourneySections = new ArrayList<TrainCompositionJourneySection>();

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_id")
	private List<TrainCompositionMessage> trainCompositionMessages = new ArrayList<TrainCompositionMessage>();

}
