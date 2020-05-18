package nl.rls.composer.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author berend.wilkens
 * Identifies dangerous goods
 */
@Entity
@NoArgsConstructor
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


    public DangerGoodsInWagon(DangerGoodsType dangerGoodsType, WagonInTrain wagonInTrain, int dangerousGoodsWeight, Float dangerousGoodsVolume) {
        this.dangerGoodsType = dangerGoodsType;
        this.wagonInTrain = wagonInTrain;
        this.dangerousGoodsWeight = dangerousGoodsWeight;
        this.dangerousGoodsVolume = dangerousGoodsVolume;
    }
}
