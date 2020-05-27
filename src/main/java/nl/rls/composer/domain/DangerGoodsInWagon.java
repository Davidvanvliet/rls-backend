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
@AllArgsConstructor
@Getter
@Setter
public class DangerGoodsInWagon {
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

    public DangerGoodsInWagon clone() {
        return new DangerGoodsInWagon(this);
    }

}
