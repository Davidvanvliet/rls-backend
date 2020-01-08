package nl.rls.composer.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.TrainActivityType;

/**
 * @author berend.wilkens
 * 
 * Defines the make up of a train for each section of its journey.
 * 
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionJourneySection extends OwnedEntity {
	public TrainCompositionJourneySection(Integer ownerId) {
		super(ownerId);
	}

	/**
	 * Origin of the section on which train composition is unchanged
	 */
	@ManyToOne
	private Location journeySectionOrigin;
	/**
	 * Destination of the section on which train composition is unchanged
	 */
	@ManyToOne
	private Location journeySectionDestination;
	/**
	 * This element identifies the responsible RU or IM for the actual path section
	 */
	@ManyToOne
	private Responsibility responsibilityActualSection;
	/**
	 * This element identifies the responsible RU and IM for the following path
	 * section
	 */
	@ManyToOne
	private Responsibility responsibilityNextSection;

	private Boolean exceptionalGaugingInd;
	private Boolean dangerousGoodsIndicator;
	/**
	 * 1 Passenger train Commercial train with passenger coaches or trainsets Empty
	 * run of Train with passenger coaches or trainsets Including Crew train (for
	 * Train Crew Members) 2 Freight train Train with freight wagons 3 Light engine
	 * (locomotive train) One or more engines without any carriages 4 Engineering
	 * train Train for measurement, maintenance, instructions, homologation, etc 0
	 * Other Train types that are not covered with the four codes given above can be
	 * codified as "other" in the messages Passenger with Freight - military trains,
	 * the Overnight Express; Royalty, Head of States
	 */
	private int trainType;
	// @ManyToMany
	// private List<TrainCC_System> trainCCSystem;
	// private TrainRadioSystem trainRadioSystem;
	private int trainMaxSpeed;
	// private BigDecimal maxAxleWeight;
	// private String brakeType;
	// private int brakeWeight;

	/**
	 * Indicates that livestock and people (other than train crew) will be carried.
	 * Coding: if live animals or people are transported = 1, in opposite case = 0.
	 * If code = 1, then at the wagon level for at least one wagon Info- Goods
	 * Shape, Type and Danger has to include the code '98' or Restrictions due to
	 * Load or Damage has to include code '09.'
	 */
	private int livestockOrPeopleIndicator;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_composition_journey_section_id")
	@OrderBy("position")
	private List<WagonInTrain> wagons = new ArrayList<WagonInTrain>();

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_composition_journey_section_id")
	@OrderBy("position")
	private List<TractionInTrain> tractions = new ArrayList<TractionInTrain>();

	@OneToMany(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "train_composition_journey_section_id")
	private List<TrainActivityType> activities = new ArrayList<>();

	@ManyToOne
	private Train train;
	
	public WagonInTrain getWagonById(Integer wagonId) {
		for (WagonInTrain wit : wagons) {
			if (wit.getId() == wagonId) {
				return wit;
			}
		}
		return null;
	}

	public boolean removeWagonById(int wagonInTrainId) {
		WagonInTrain entity = getWagonById(wagonInTrainId);
		if (entity != null) {
			removeWagon(entity);
			return true;
		} else {
			return false;
		}
	}

	public void removeWagon(WagonInTrain wagonInTrain) {
		wagonInTrain.setTrainCompositionJourneySection(null);
		wagons.remove(wagonInTrain);
		int pos = 1;
		for (WagonInTrain wit : wagons) {
			if (wit.getPosition() != pos) {
				wit.setPosition(pos);
			}
			pos++;
		}
	}

	public void addWagon(WagonInTrain wagonInTrain) {
		if (wagonInTrain.getPosition() <= 0 || wagonInTrain.getPosition() > wagons.size()) {
			wagonInTrain.setPosition(1);
		}
		wagons.add(wagonInTrain.getPosition() - 1, wagonInTrain);
		wagonInTrain.setTrainCompositionJourneySection(this);
		int pos = 1;
		for (WagonInTrain wit : wagons) {
			if (wit.getPosition() != pos) {
				wit.setPosition(pos);
			}
			pos++;
		}
	}

	public void moveWagonById(int wagonInTrainId, int position) {
		moveWagon(getWagonById(wagonInTrainId), position);
	}

	public void moveWagon(WagonInTrain wagonInTrain, int position) {
		removeWagon(wagonInTrain);
		wagonInTrain.setPosition(position);
		addWagon(wagonInTrain);
	}

	public TractionInTrain getTractionById(Integer id) {
		for (TractionInTrain tit : tractions) {
			if (tit.getId() == id) {
				return tit;
			}
		}
		return null;
	}

	public void addTraction(TractionInTrain entity) {
		if (entity.getPosition() <= 0 || entity.getPosition() > tractions.size()) {
			entity.setPosition(1);
		}
		tractions.add(entity.getPosition() - 1, entity);
		entity.setTrainCompositionJourneySection(this);
		int pos = 1;
		for (TractionInTrain tit : tractions) {
			if (tit.getPosition() != pos) {
				tit.setPosition(pos);
			}
			pos++;
		}
	}

	public void moveTractionById(int id, int position) {
		moveTraction(getTractionById(id), position);
	}

	public void moveTraction(TractionInTrain entity, int position) {
		removeTraction(entity);
		entity.setPosition(position);
		addTraction(entity);
	}

	public void removeTractionById(int id) {
		TractionInTrain entity = getTractionById(id);
		if (entity != null) {
			removeTraction(entity);
		}
	}

	public void removeTraction(TractionInTrain entity) {
		entity.setTrainCompositionJourneySection(null);
		tractions.remove(entity);
		int pos = 1;
		for (TractionInTrain tit : tractions) {
			if (tit.getPosition() != pos) {
				tit.setPosition(pos);
			}
			pos++;
		}
	}

	public TrainActivityType getActivityById(Integer id) {
		for (TrainActivityType ait : activities) {
			if (ait.getId() == id) {
				return ait;
			}
		}
		return null;
	}

	public boolean addActivity(TrainActivityType entity) {
		if (!activities.contains(entity)) {
			activities.add(entity);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeActivityById(int id) {
		TrainActivityType entity = getActivityById(id);
		if (entity != null) {
			removeActivity(entity);
			return true;
		} else {
			return false;
		}
	}

	public void removeActivity(TrainActivityType entity) {
		activities.remove(entity);
	}

	/**
	 * The sum of all weights of wagons and traction units
	 */
	public int getTrainWeight() {
		int trainWeight = 0;
		for (WagonInTrain wagon : getWagons()) {
			trainWeight += wagon.getWagonLoad().getWagon().getWagonTechData().getWagonWeightEmpty();
			trainWeight += wagon.getWagonLoad().getTotalLoadWeight();
		}
		for (TractionInTrain traction : getTractions()) {
			trainWeight += traction.getTraction().getWeight();
		}
		return trainWeight;
	}

	/**
	 * The calculated Length of a train (sum of all length over buffer of the wagons
	 * and traction units). Expressed in Metres
	 */
	public int getTrainLength() {
		int trainLength = 0;
		for (WagonInTrain wagon : getWagons()) {
			trainLength += wagon.getWagonLoad().getWagon().getWagonTechData().getLengthOverBuffers();
		}
		for (TractionInTrain traction : getTractions()) {
			trainLength += traction.getTraction().getLengthOverBuffers();
		}
		return trainLength;
	}

	public int getNumberOfVehicles() {
		int numberOfVehicles = getWagons().size();
		numberOfVehicles += getTractions().size();
		return numberOfVehicles;
	}

	public int getNumberOfAxles() {
		int numberOfAxles = 0;
		for (WagonInTrain wagon : getWagons()) {
			numberOfAxles += wagon.getWagonLoad().getWagon().getWagonTechData().getWagonNumberOfAxles();
		}
		for (TractionInTrain traction : getTractions()) {
			numberOfAxles += traction.getTraction().getNumberOfAxles();
		}
		return numberOfAxles;
	}

}
