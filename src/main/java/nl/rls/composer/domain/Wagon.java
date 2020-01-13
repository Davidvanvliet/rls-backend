package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author berend.wilkens
 * Identification code of a freight wagon based on the TSI OPE and CEN Recommendations and CIS wagons coded according to OSJD-UIC leaflet 402, which allows the conversion from 8 digits to 12 digits and viceversa.
 */
@Entity
@NoArgsConstructor
@Getter @Setter
public class Wagon extends OwnedEntity{
	private String numberFreight;
    @ManyToOne
    private WagonType wagonType;
}
