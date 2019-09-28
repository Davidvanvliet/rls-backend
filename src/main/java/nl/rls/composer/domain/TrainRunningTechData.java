package nl.rls.composer.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens Shows the relevant techical data for a running train
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TrainRunningTechData extends OwnedEntity {
	/**
	 * 1 Passenger train Commercial train with passenger coaches or trainsets Empty
	 * run of Train with passenger coaches or trainsets Including Crew train (for
	 * Train Crew Members) 
	 * 2 Freight train Train with freight wagons 
	 * 3 Light engine (locomotive train) One or more engines without any carriages 
	 * 4 Engineering train Train for measurement, maintenance, instructions, homologation, etc 
	 * 0 Other Train types that are not covered with the four codes given above can be
	 * codified as "other" in the messages Passenger with Freight - military trains,
	 * the Overnight Express; Royalty, Head of States
	 */
	private int trainType;
	/**
	 * The sum of all weights of wagons and traction units
	 */
	private int trainWeight;
	/**
	 * The calculated Length of a train (sum of all length over buffer of the wagons and traction units). Expressed in Metres
	 */
	private int trainLength;
	/**
	 * Type of Train Control System.
	 */
//	@ManyToMany
//	private List<TrainCC_System> trainCCSystem;
//	private TrainRadioSystem trainRadioSystem;
	private int trainMaxSpeed;
//	private BigDecimal maxAxleWeight;
//	private String brakeType;
//	private int brakeWeight;
	private int numberOfVehicles;
	private int numberOfAxles;

}
