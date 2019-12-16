package nl.rls.composer.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.DangerLabel;

/**
 * @author berend.wilkens
 * This element indicates the type of a dangerous load
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class DanGoodsType {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
    private String hazardIdentificationNumber;
    /**
     * The UNNumber of the dangerous good according to the RID chapter 3.2, table A, column 1. Mandatory, except it concerns a declaration of an empty packaging of the type "EMPTY PACKAGING", "EMPTY RECEPTACLE &lt;=1000L", "EMPTY IBC" or "EMPTY LARGE PACKAGING" .
     */
    private String unNumber;
    @OneToMany
    private List<DangerLabel> dangerLabel;
    /**
     * The Class of the dangerous good according to the RID chapter 3.2, table A, column 3a.
     */
    private String ridClass;
    private String packingGroup;
    /**
     * The weight of the dangerous goods in Kilograms
     */
    private int dangerousGoodsWeight;
    /**
     * The volume of the dangerous goods in cubic meters
     */
    private Float dangerousGoodsVolume;
    /**
     * Indicator for labelled dangerous goods in limited quantity according to chapter 3.-4 RID
     */
    private boolean limitedQuantityIndicator;
}
