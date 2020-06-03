package nl.rls.composer.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "trainComposition")
    @OrderBy("position")
    @OrderColumn(name = "position")
    private List<RollingStock> rollingStock = new ArrayList<>();

    @OneToOne(mappedBy = "trainComposition")
    private JourneySection journeySection;

    public TrainComposition(Integer ownerId) {
        super(ownerId);
    }

    public TrainComposition(@NotNull TrainComposition trainComposition) {
        this.brakeType = trainComposition.brakeType;
        this.livestockOrPeopleIndicator = trainComposition.livestockOrPeopleIndicator;
        for (RollingStock stock : trainComposition.rollingStock) {
            RollingStock rollingStock = stock.clone();
            rollingStock.setTrainComposition(this);
            addRollingStock(rollingStock);
        }
        this.journeySection = trainComposition.journeySection;
    }

    public boolean isGaugedExceptional() {
        return false;
    }

    public boolean getContainsDangerousGoods() {
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
    public int getWeight() {
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
    public int getLength() {
        double trainLength = 0;
        for (RollingStock stock : rollingStock) {
            trainLength += stock.getLength();
        }
        return Math.toIntExact(Math.round(trainLength / 100));
    }

    public int getNumberOfVehicles() {
        return rollingStock.size();
    }

    public int getNumberOfAxles() {
        int numberOfAxles = 0;
        for (RollingStock stock : rollingStock) {
            numberOfAxles += stock.getNumberOfAxles();
        }
        return numberOfAxles;
    }

    public int getMaxSpeed() {
        List<RollingStock> stock = rollingStock
                .stream()
                .sorted(Comparator.comparingInt(RollingStock::getMaxSpeed))
                .filter((rollingStock1 -> rollingStock1.getMaxSpeed() != 0))
                .collect(Collectors.toList());
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

    public void addRollingStock(RollingStock rollingStock) {
        this.rollingStock.add(rollingStock);
    }

    public boolean hasRollingStock(Long stockIdentifier) {
        for (RollingStock stock : this.rollingStock) {
            if (stock.getStockIdentifier().equals(stockIdentifier)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRollingStock(int rollingStockId) {
        for (RollingStock stock : this.rollingStock) {
            if (stock.getId().equals(rollingStockId)) {
                return true;
            }
        }
        return false;

    }

    public void removeRollingStock(int rollingStockId) {
        this.rollingStock.removeIf(stock -> stock.getId().equals(rollingStockId));
    }

    public RollingStock getRollingStock(int rollingStockId) {
        for (RollingStock stock : this.rollingStock) {
            if (stock.getId().equals(rollingStockId)) {
                return stock;
            }
        }
        return null;
    }

    public TrainComposition clone() {
        return new TrainComposition(this);
    }
}
