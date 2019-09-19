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
 * Identifies the mode of deployment of a traction within a train First digit –
 * traction role Second digit – position in group of traction units with the
 * same role
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class TractionMode {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String code;
	private String description;

}
