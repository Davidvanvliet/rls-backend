package nl.rls.composer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author berend.wilkens
 * Identifies dangerous goods
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class DangerGoodsInWagon implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private DangerGoodsType dangerGoodsType;
    @ManyToOne
    private WagonInTrain wagonInTrain;

    /**
     * The weight of the dangerous goods in Kilograms
     */
    private int dangerousGoodsWeight;
    /**
     * The volume of the dangerous goods in cubic meters
     */
    private Float dangerousGoodsVolume;

    public DangerGoodsInWagon(DangerGoodsInWagon dangerGoodsInWagon) {
        this.dangerGoodsType = dangerGoodsInWagon.dangerGoodsType;
        this.wagonInTrain = dangerGoodsInWagon.wagonInTrain;
        this.dangerousGoodsWeight = dangerGoodsInWagon.dangerousGoodsWeight;
        this.dangerousGoodsVolume = dangerGoodsInWagon.dangerousGoodsVolume;
    }


    public DangerGoodsInWagon(DangerGoodsType dangerGoodsType, WagonInTrain wagonInTrain, int dangerousGoodsWeight, Float dangerousGoodsVolume) {
        this.dangerGoodsType = dangerGoodsType;
        this.wagonInTrain = wagonInTrain;
        this.dangerousGoodsWeight = dangerousGoodsWeight;
        this.dangerousGoodsVolume = dangerousGoodsVolume;
    }

    public DangerGoodsInWagon clone() {
        try {
            return (DangerGoodsInWagon) super.clone();
        } catch (CloneNotSupportedException e) {
            return new DangerGoodsInWagon(this);
        }
    }

}
