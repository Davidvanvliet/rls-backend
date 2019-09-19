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
 * Identification of special load.  Coding found in 404-2 chapter 4.9.1 (4AN + 3N)
 * 
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class ExceptionalGaugingProfile {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String value;
}

