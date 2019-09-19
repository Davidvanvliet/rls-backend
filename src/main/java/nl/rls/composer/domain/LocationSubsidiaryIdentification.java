package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens
 * Code, Name and allocation company of Subsidiary Location
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class LocationSubsidiaryIdentification {
	@Id 	
	private int id;
	/**
	 * this element identifies a location as a part of primary location e.g. a junction, a signal, a passing loop etc., It is unique when used in combination with a “LocationPrimaryCode”
	 */
    private String locationSubsidiaryCode;
	/**
	 * Name of company who is responsible for allocation and maintenance of codes
	 */
	@ManyToOne
    private Company allocationCompany;
	/**
	 * To be completed in an official language of the Country using the ISO Unicode alphabet
	 */
    private String locationSubsidiaryName;
    private String extra;
}
