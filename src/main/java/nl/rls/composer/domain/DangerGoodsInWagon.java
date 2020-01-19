package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
public class DangerGoodsInWagon {

	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
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
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dangerGoodsType == null) ? 0 : dangerGoodsType.hashCode());
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DangerGoodsInWagon other = (DangerGoodsInWagon) obj;
		if (dangerGoodsType == null) {
			if (other.dangerGoodsType != null)
				return false;
		} else if (!dangerGoodsType.equals(other.dangerGoodsType))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
