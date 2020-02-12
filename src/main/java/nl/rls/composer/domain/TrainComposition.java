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

/**
 * @author berend.wilkens
 * 
 *         Defines the make up of a train for each section of its journey.
 * 
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TrainComposition extends OwnedEntity {
	public TrainComposition(Integer ownerId) {
		super(ownerId);
	}

	public Boolean getExceptionalGaugingIndicator() {
		return false;
	}

	public Boolean getDangerousGoodsIndicator() {
		return false;
	}

	// @ManyToMany
	// private List<TrainCC_System> trainCCSystem;
	// private TrainRadioSystem trainRadioSystem;
	private int trainMaxSpeed;
	private int maxAxleWeight;
	private String brakeType;
	private int brakeWeight;

	/**
	 * Indicates that livestock and people (other than train crew) will be carried.
	 * Coding: if live animals or people are transported = 1, in opposite case = 0.
	 * If code = 1, then at the wagon level for at least one wagon Info- Goods
	 * Shape, Type and Danger has to include the code '98' or Restrictions due to
	 * Load or Damage has to include code '09.'
	 */
	private int livestockOrPeopleIndicator;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_composition_id")
	@OrderBy("position")
	private List<WagonInTrain> wagons = new ArrayList<WagonInTrain>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_composition_id")
	@OrderBy("position")
	private List<TractionInTrain> tractions = new ArrayList<TractionInTrain>();

	@ManyToOne
	private JourneySection journeySection;

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
		wagonInTrain.setTrainComposition(null);
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
		wagonInTrain.setPosition(wagons.size() + 1);
		wagons.add(wagonInTrain);
	}

	public void moveWagonById(int wagonInTrainId, int position) {
		WagonInTrain wagonInTrain = getWagonById(wagonInTrainId);
		if (wagonInTrain != null) {
			moveWagon(wagonInTrain, position);
		} else {
			System.out.println("WagonInTrain does not extist " + wagonInTrainId);
		}
	}

	public void moveWagon(WagonInTrain wagonInTrain, int newPosition) {
		int oldPosition = wagonInTrain.getPosition();
		System.out.println("moveWagon old " + oldPosition + ", new " + newPosition);
		if (newPosition <= 0 || newPosition > wagons.size() || oldPosition == newPosition) {
			return;
		}
		if (oldPosition < newPosition) {
			for (WagonInTrain wit : wagons) {
				int currentPosition = wit.getPosition();
				if (currentPosition <= newPosition && currentPosition > oldPosition) {
					currentPosition = currentPosition - 1;
					wit.setPosition(currentPosition);
				}
			}
		} else {
			for (WagonInTrain wit : wagons) {
				int currentPosition = wit.getPosition();
				if (currentPosition >= newPosition && currentPosition < oldPosition) {
					currentPosition = currentPosition + 1;
					wit.setPosition(currentPosition);
				}
			}
		}
		wagonInTrain.setPosition(newPosition);
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
		entity.setTrainComposition(this);
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
		entity.setTrainComposition(null);
		tractions.remove(entity);
		int pos = 1;
		for (TractionInTrain tit : tractions) {
			if (tit.getPosition() != pos) {
				tit.setPosition(pos);
			}
			pos++;
		}
	}

	/**
	 * The sum of all weights of wagons and traction units
	 */
	public Integer getTrainWeight() {
		int trainWeight = 0;
		for (WagonInTrain wagon : getWagons()) {
			if (wagon.getWagon() != null) {
				trainWeight += wagon.getWagon().getWagonWeightEmpty();
				trainWeight += wagon.getTotalLoadWeight();
			}
		}
		for (TractionInTrain traction : getTractions()) {
			trainWeight += traction.getTraction().getWeight();
		}
		return trainWeight;
	}

	/**
	 * The calculated Length of a train (sum of all length over buffer of the wagons
	 * and traction units). Expressed in Meters
	 */
	public Integer getTrainLength() {
		int trainLength = 0;
		for (WagonInTrain wagon : getWagons()) {
			trainLength += wagon.getWagon().getLengthOverBuffers();
		}
		for (TractionInTrain traction : getTractions()) {
			trainLength += traction.getTraction().getLengthOverBuffers();
		}
		return trainLength;
	}

	public Integer getNumberOfVehicles() {
		int numberOfVehicles = getWagons().size();
		numberOfVehicles += getTractions().size();
		return numberOfVehicles;
	}

	public Integer getNumberOfAxles() {
		Integer numberOfAxles = 0;
		for (WagonInTrain wagon : getWagons()) {
			numberOfAxles += wagon.getWagon().getWagonNumberOfAxles();
		}
		for (TractionInTrain traction : getTractions()) {
			numberOfAxles += traction.getTraction().getNumberOfAxles();
		}
		return numberOfAxles;
	}

}
