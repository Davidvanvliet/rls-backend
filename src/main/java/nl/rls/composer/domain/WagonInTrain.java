package nl.rls.composer.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.BrakeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("wagon")
@NoArgsConstructor
@Getter
@Setter
public class WagonInTrain extends RollingStock {
    /**
     * Actual wagon parameters, dependent on load or damage. This group and its elements are optional (contract defines what IM requires). But if there is dangerous goods in the train, then this group is mandatory.
     */
    private BrakeType brakeType;

    private int totalLoadWeight;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wagon_in_train_id")
    private List<DangerGoodsInWagon> dangerGoodsInWagons = new ArrayList<>();

    @ManyToOne(optional = false)
    private Wagon wagon;

    public WagonInTrain(@NotNull WagonInTrain wagonInTrain) {
        this.brakeType = wagonInTrain.brakeType;
        this.totalLoadWeight = wagonInTrain.totalLoadWeight;
        this.wagon = wagonInTrain.wagon;
        for (DangerGoodsInWagon dangerGoodsInWagon : wagonInTrain.dangerGoodsInWagons) {
            this.addDangerGoodsInWagon(dangerGoodsInWagon.clone());
        }
    }

    @Override
    public Long getStockIdentifier() {
        return Long.parseLong(wagon.getNumberFreight());
    }

    @Override
    public boolean isGaugedExceptional() {
        return false;
    }

    @Override
    public boolean containsDangerousGoods() {
        return !dangerGoodsInWagons.isEmpty();
    }

    @Override
    public int getTotalWeight() {
        return wagon.getWagonWeightEmpty() + totalLoadWeight;
    }

    @Override
    public int getLoadWeight() {
        return totalLoadWeight;
    }
    @Override
    public int getLength() {
        return wagon.getLengthOverBuffers();
    }

    @Override
    public int getNumberOfAxles() {
        return wagon.getWagonNumberOfAxles();
    }

    @Override
    public int getMaxSpeed() {
        return wagon.getMaxSpeed();
    }

    @Override
    public RollingStock clone() {
        return new WagonInTrain(this);
    }

    public void addDangerGoodsInWagon(DangerGoodsInWagon dangerGoodsInWagon) {
        this.dangerGoodsInWagons.add(dangerGoodsInWagon);
    }
}
