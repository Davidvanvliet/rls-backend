package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author berend.wilkens
 * Identifies dangerous goods
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class DangerousGoodsDetails {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	@OneToOne
    private DanGoodsType dangerousGoodsIndication;
	/**
	 * Requested by RID specification, weight in kilograms
	 */
    private Float weightOfDangerousGoods;
}
