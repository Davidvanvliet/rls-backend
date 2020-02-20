package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author berend.wilkens
 * This element indicates the type of a dangerous load
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class DangerGoodsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String hazardIdentificationNumber;
    /**
     * The UNNumber of the dangerous good according to the RID chapter 3.2, table A, column 1. Mandatory, except it concerns a declaration of an empty packaging of the type "EMPTY PACKAGING", "EMPTY RECEPTACLE &lt;=1000L", "EMPTY IBC" or "EMPTY LARGE PACKAGING" .
     */
    private String unNumber;
//    @OneToMany
//	@JoinColumn(name = "dan_goods_type_id")
//    private List<DangerLabel> dangerLabel;
    /**
     * The Class of the dangerous good according to the RID chapter 3.2, table A, column 3a.
     */
    private String ridClass;
    private String packingGroup;
    /**
     * Indicator for labelled dangerous goods in limited quantity according to chapter 3.-4 RID
     */
    private boolean limitedQuantityIndicator;
}
