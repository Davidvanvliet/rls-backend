package nl.rls.composer.rest.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

/**
 * @author berend.wilkens 
 * Used for unique identification of the objects handled
 * in the messages such as train, path, path request or case reference.
 */
@ToString
@NoArgsConstructor
@Getter
@Setter
public class CompositIdentifierOperationalTypeDto extends ResourceSupport {
	private String objectType;
	private CompanyDto company;
	private String core;
	private String variant;
	private int timetableYear;
	private Date startDate;
}
