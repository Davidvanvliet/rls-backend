package nl.rls.composer.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author berend.wilkens 
 * Composite identifier for the loco types and locomotives. First four 
 * elements identify the series of the loco, rest can identify the exact 
 * individual locomotive.
 */
@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class LocoTypeNumber extends OwnedEntity {
	/**
	 * Value is 9 as it's written in Part 0 of the Appendix 6 of the decision 2007/756
	 */
	private String typeCode1;
	/**
	 * Type of tractive rolling stock as in Part 8 of the Appendix 6 of the decision 2007/756
	 */
	private String typeCode2;
	/**
	 * Numerical country code as in Part 4 of the Appendix 6 of the decision 2007/756
	 */
	private String countryCode;
	/**
	 * 4 digits representing the type according to the country rules and based on the national vehicle register of the country indicated with the CountryCode
	 */
	private String seriesNumber;
	/**
	 * 3 digits representing the serial number of the traction of the series. Not used in Planning
	 */
	private String serialNumber;
	/**
	 * 1 control digit as usual at the end of the 12 digit UIC identifier. Not used in Planning
	 */
	private String controlDigit;
}
