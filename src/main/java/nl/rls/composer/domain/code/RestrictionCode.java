package nl.rls.composer.domain.code;

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
 * All codes of Transport restrictions for Freight Traffic (cf. UIC 920-13) and Passengers Traffic are in the same list. 
									F = Freight
P = Passenger
T = Technical
D = Damage
L = Load
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class RestrictionCode {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id;
	private String code;
	private String forp;
	private String description;

}
