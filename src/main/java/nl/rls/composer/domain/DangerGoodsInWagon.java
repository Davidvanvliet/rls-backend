package nl.rls.composer.domain;

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

}
