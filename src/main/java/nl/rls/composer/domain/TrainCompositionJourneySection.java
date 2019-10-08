package nl.rls.composer.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.TrainActivityType;

/**
 * @author berend.wilkens Defines the make up of a train for each section of its
 *         journey
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionJourneySection extends OwnedEntity {
	public TrainCompositionJourneySection(Integer ownerId) {
		super(ownerId);
	}

	@ManyToOne
	private JourneySection journeySection;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "train_running_data_id")
	private TrainRunningData trainRunningData;
	/**
	 * Indicates that livestock and people (other than train crew) will be carried.
	 * Coding: if live animals or people are transported = 1, in opposite case = 0.
	 * If code = 1, then at the wagon level for at least one wagon Info- Goods
	 * Shape, Type and Danger has to include the code '98' or Restrictions due to
	 * Load or Damage has to include code '09.'
	 */
	private int livestockOrPeopleIndicator;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_composition_journey_section_id")
	@OrderBy("wagonTrainPosition")
	private List<WagonInTrain> wagons = new ArrayList<WagonInTrain>();
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "train_composition_journey_section_id")
	@OrderBy("tractionPositionInTrain")
	private List<TractionInTrain> tractions = new ArrayList<TractionInTrain>();

	@OneToMany(cascade = {CascadeType.MERGE })
	@JoinColumn(name = "train_composition_journey_section_id")
	private List<TrainActivityType> activities = new ArrayList<>();

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
		wagons.remove(wagonInTrain);
		int pos = 1;
		for (WagonInTrain wit : wagons) {
			if (wit.getWagonTrainPosition() != pos) {
				wit.setWagonTrainPosition(pos);
			}
			pos++;
		}
	}

	public void addWagon(WagonInTrain wagonInTrain) {
		if (wagonInTrain.getWagonTrainPosition() <= 0 || wagonInTrain.getWagonTrainPosition() > wagons.size()) {
			wagonInTrain.setWagonTrainPosition(1);
		}
		wagons.add(wagonInTrain.getWagonTrainPosition()-1, wagonInTrain);
		int pos = 1;
		for (WagonInTrain wit : wagons) {
			if (wit.getWagonTrainPosition() != pos) {
				wit.setWagonTrainPosition(pos);
			}
			pos++;
		}
	}

	public void moveWagonById(int wagonInTrainId, int position) {
		moveWagon(getWagonById(wagonInTrainId), position);
	}

	public void moveWagon(WagonInTrain wagonInTrain, int position) {
		removeWagon(wagonInTrain);
		wagonInTrain.setWagonTrainPosition(position);
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
		if (entity.getTractionPositionInTrain() <= 0 || entity.getTractionPositionInTrain() > wagons.size()) {
			entity.setTractionPositionInTrain(1);
		}
		tractions.add(entity.getTractionPositionInTrain()-1, entity);
		int pos = 1;
		for (WagonInTrain wit : wagons) {
			if (wit.getWagonTrainPosition() != pos) {
				wit.setWagonTrainPosition(pos);
			}
			pos++;
		}
	}

	public void moveTractionById(int id, int position) {
		moveTraction(getTractionById(id), position);
	}

	public void moveTraction(TractionInTrain entity, int position) {
		removeTraction(entity);
		entity.setTractionPositionInTrain(position);
		addTraction(entity);
	}

	public boolean removeTractionById(int id) {
		TractionInTrain entity = getTractionById(id);
		if (entity != null) {
			removeTraction(entity);
			return true;
		} else {
			return false;
		}
	}

	public void removeTraction(TractionInTrain entity) {
		tractions.remove(entity);
		int pos = 1;
		for (TractionInTrain tit : tractions) {
			if (tit.getTractionPositionInTrain() != pos) {
				tit.setTractionPositionInTrain(pos);
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
}
