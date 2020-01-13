package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TractionInTrain {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	/**
	 * 0 - no driver present in Loco, 1 - driver(s) is /are) present in Loco
	 */
	protected Integer driverIndication;
	/**
	 * Identifies position of intermediate traction unit(s) in the train indicating
	 * after which wagon (specified by order number) the unit is placed.
	 */
	private Integer position;
    @ManyToOne
    private Traction traction;
    @ManyToOne
    private TrainComposition trainComposition;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TractionInTrain other = (TractionInTrain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
