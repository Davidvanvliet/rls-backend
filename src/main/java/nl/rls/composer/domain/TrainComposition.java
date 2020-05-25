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
    @OrderBy("position")
    private List<RollingStock> rollingStock = new ArrayList<>();

    @OneToOne(mappedBy = "trainComposition")
    private JourneySection journeySection;

    public TrainComposition(Integer ownerId) {
        super(ownerId);
    }

    public boolean isGaugedExceptional() {
        return false;
    }

    public boolean containsDangerousGoods() {
        for (RollingStock stock : rollingStock) {
            if (stock.containsDangerousGoods()) {
                return true;
            }
        }
        return false;
    }

    /**
     * The sum of the weight of the train, including load in kilos
     */
    public int getTotalWeight() {
        int trainWeight = 0;
        for (RollingStock stock : rollingStock) {
            trainWeight += stock.getTotalWeight();
        }
        return trainWeight;
    }

    /**
     * The calculated Length of a train (sum of all length over buffer of the wagons
     * and traction units). Expressed in Meters
     */
    public int getTrainLength() {
        double trainLength = 0;
        for (RollingStock stock : rollingStock) {
            trainLength += stock.getLength();
        }
        return Math.toIntExact(Math.round(trainLength / 100));
    }

    public int getRollingStockCount() {
        return rollingStock.size();
    }

    public int getNumberOfAxles() {
        int numberOfAxles = 0;
        for (RollingStock stock : rollingStock) {
            numberOfAxles += stock.getNumberOfAxles();
        }
        return numberOfAxles;
    }

    public int getTrainMaxSpeed() {
        List<RollingStock> stock = rollingStock.stream().sorted(Comparator.comparingInt(RollingStock::getMaxSpeed)).collect(Collectors.toList());
        if (stock.size() == 0) {
            return 0;
        }
        return stock.get(0).getMaxSpeed();
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
