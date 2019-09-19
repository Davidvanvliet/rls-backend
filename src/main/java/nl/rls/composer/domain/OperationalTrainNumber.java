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
 * Identifies the train for traffic management purposes by the Dispatcher, GSMR services, etc.
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class OperationalTrainNumber {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String value;
	private String description;


}
