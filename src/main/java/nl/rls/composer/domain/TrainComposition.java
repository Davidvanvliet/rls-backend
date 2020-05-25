package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


// TODO rounding of numbers by divinding
/**
 * @author berend.wilkens
 * <p>
 * Defines the make up of a train for each section of its journey.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TrainComposition extends OwnedEntity {
    // @ManyToMany
    // private List<TrainCC_System> trainCCSystem;
    // private TrainRadioSystem trainRadioSystem;
    private String brakeType;
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
    @OneToOne(mappedBy = "trainComposition")
    private JourneySection journeySection;

    public TrainComposition(Integer ownerId) {
        super(ownerId);
    }

    public Boolean getExceptionalGaugingIndicator() {
        return false;
    }

    public Boolean getDangerousGoodsIndicator() {
        return false;
    }

    public WagonInTrain getWagonById(Integer wagonId) {
        for (WagonInTrain wagonInTrain : wagons) {
            System.out.println(wagonInTrain);
            if (wagonInTrain.getId().equals(wagonId)) {
                return wagonInTrain;
            }
        }
        return null;
    }

    public boolean removeWagonById(int wagonInTrainId) {
        WagonInTrain entity = getWagonById(wagonInTrainId);
        if (entity != null) {
            return removeWagon(entity);
        }
        throw new EntityNotFoundException(String.format("Wagon not found with id %d", wagonInTrainId));
    }

    public boolean removeWagon(WagonInTrain wagonInTrain) {
        wagonInTrain.setTrainComposition(null);
        boolean removed = wagons.remove(wagonInTrain);
        int pos = 1;
        for (WagonInTrain wit : wagons) {
            if (wit.getPosition() != pos) {
                wit.setPosition(pos);
            }
            pos++;
        }
        return removed;
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
     * The sum of all weights of wagons and traction units in tons
     */
    public Integer getTrainWeight() {
        int trainWeight = 0;
        for (WagonInTrain wagon : getWagons()) {
            trainWeight += wagon.getWeight();
        }
        for (TractionInTrain traction : getTractions()) {
            trainWeight += traction.getWeight();
        }
        return trainWeight;
    }

    /**
     * The calculated Length of a train (sum of all length over buffer of the wagons
     * and traction units). Expressed in Meters
     */
    public Integer getTrainLength() {
        double trainLength = 0;
        for (WagonInTrain wagon : getWagons()) {
            trainLength += wagon.getWagon().getLengthOverBuffers();
        }
        for (TractionInTrain traction : getTractions()) {
            trainLength += traction.getTraction().getLengthOverBuffers();
        }
        return Math.toIntExact(Math.round(trainLength / 100));
    }

    public Integer getNumberOfVehicles() {
        int numberOfVehicles = getWagons().size();
        numberOfVehicles += getTractions().size();
        return numberOfVehicles;
    }

    public Integer getNumberOfAxles() {
        int numberOfAxles = 0;
        for (WagonInTrain wagon : getWagons()) {
            numberOfAxles += wagon.getNumberOfAxles();
        }
        for (TractionInTrain traction : getTractions()) {
            numberOfAxles += traction.getNumberOfAxles();
        }
        return numberOfAxles;
    }

    public Integer getTrainMaxSpeed() {
        List<WagonInTrain> wagonInTrains = wagons.stream().sorted(Comparator.comparingInt(WagonInTrain::getMaxSpeed)).collect(Collectors.toList());
        if (wagonInTrains.size() == 0) {
            return 0;
        }
        return wagonInTrains.get(0).getMaxSpeed();
    }

    public Integer getBrakeWeight() {
        //TODO: verder uitwerken
        return 100;
    }

    public Integer getMaxAxleWeight() {
        //TODO: verder uitwerken
        return 10;
    }
    


}
