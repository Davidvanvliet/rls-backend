package nl.rls.composer.domain;

import java.util.Date;

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
 * 
 * Used for unique identification of the objects handled in the messages such as train, 
 * path, path request or case refernce.
 */
@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class CompositIdentifierOperationalType {
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
    private String objectType;
    @ManyToOne
    private Company company;
    private String core;
    private String variant;
    private int timetableYear;
    private Date startDate;
}
