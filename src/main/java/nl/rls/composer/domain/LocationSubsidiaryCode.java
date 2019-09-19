package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens
 * Code list
 *
 */
@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class LocationSubsidiaryCode {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	/**
	 * LocationSubsidiaryTypeCode
	 */
    private String value;
    private String description;
    private String extra;
}
