package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens
 * Identification code of a freight wagon based on the TSI OPE and CEN Recommendations and CIS wagons coded according to OSJD-UIC leaflet 402, which allows the conversion from 8 digits to 12 digits and viceversa.
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class WagonIdent extends OwnedEntity{
	private String wagonNumberFreight;
    @ManyToOne
    private WagonTechData wagonTechData;
}
