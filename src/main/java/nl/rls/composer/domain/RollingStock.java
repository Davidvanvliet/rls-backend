package nl.rls.composer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stock_type")
@Getter
@Setter
public abstract class RollingStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "position")
    private int position;

    @Column(name = "stock_type", insertable = false, updatable = false)
    private String stockType;

    @ManyToOne
    @JoinColumn(name = "train_composition_id")
    private TrainComposition trainComposition;

    /**
     * @return unique UIC code
     */
    public abstract Long getStockIdentifier();

    /**
     * @return if the rolling stock is gauged exceptional
     */
    public abstract boolean isGaugedExceptional();

    /**
     * @return if the rolling stock contains dangerous goods
     */
    public abstract boolean containsDangerousGoods();

    /**
     * @return weight of the rolling stock in kilos
     */
    public abstract int getTotalWeight();

    /**
     * @return weight of the load in kilos
     */
    public abstract int getLoadWeight();

    /**
     * @return length of the rolling stock in centimeter
     */
    public abstract int getLength();

    /**
     * @return number of axles for the rolling stock
     */
    public abstract int getNumberOfAxles();

    /**
     * @return max weight the axle can have
     */
    public abstract int getMaxAxleWeight();

    /**
     * @return max speed in km/h
     */
    public abstract int getMaxSpeed();
}
