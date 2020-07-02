package nl.rls.composer.domain;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("traction")
@EqualsAndHashCode
public class TractionInTrain extends RollingStock implements Cloneable {

    @ManyToOne(optional = false)
    private Traction traction;
    /**
     * 0 - no driver present in Loco, 1 - driver(s) is /are) present in Loco
     */
    protected Integer driverIndication;

    public TractionInTrain(@NotNull TractionInTrain tractionInTrain) {
        this.traction = tractionInTrain.traction;
        this.driverIndication = tractionInTrain.driverIndication;
    }

    @Override
    public Long getStockIdentifier() {
        return traction.getLocoTypeNumber();
    }

    @Override
    public boolean isGaugedExceptional() {
        return false;
    }

    @Override
    public boolean containsDangerousGoods() {
        return false;
    }

    @Override
    public int getTotalWeight() {
        return traction.getWeight();
    }
  
    @Override
    public int getLoadWeight() {
        return 0;
    }

    @Override
    public int getLength() {
        return traction.getLengthOverBuffers();
    }

    @Override
    public int getNumberOfAxles() {
        return traction.getNumberOfAxles();
    }

    @Override
    public int getMaxSpeed() {
        // TODO make this not hardcoded
        return 1;
    }

    @Override
    public int getBrakeWeight() {
        return traction.getBrakeWeightG();
    }

    @Override
    public RollingStock clone() {
        TractionInTrain tractionInTrain;
        try {
            tractionInTrain = (TractionInTrain) super.clone();
        } catch (CloneNotSupportedException e) {
            tractionInTrain = new TractionInTrain(this);
        }
        tractionInTrain.setId(null);
        return tractionInTrain;
    }
}
