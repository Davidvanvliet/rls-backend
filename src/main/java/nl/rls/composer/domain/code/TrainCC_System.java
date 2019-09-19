package nl.rls.composer.domain.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class TrainCC_System {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;	
	private String value;
	private String description;

}
