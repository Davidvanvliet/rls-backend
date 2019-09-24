package nl.rls.composer.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class OperationalTrainNumberIdentifier {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
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
    
	public OperationalTrainNumberIdentifier(String operationalTrainNumber, Date scheduledTimeAtHandover,
			Date scheduledDateTimeAtTransfer) {
		super();
		this.operationalTrainNumber = operationalTrainNumber;
		this.scheduledTimeAtHandover = scheduledTimeAtHandover;
		this.scheduledDateTimeAtTransfer = scheduledDateTimeAtTransfer;
	}

}
