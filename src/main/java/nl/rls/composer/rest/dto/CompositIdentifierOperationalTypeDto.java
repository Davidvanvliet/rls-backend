package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

/**
 * @author berend.wilkens
 * Used for unique identification of the objects handled
 * in the messages such as train, path, path request or case reference.
 */
@ToString
@NoArgsConstructor
@Getter
@Setter
public class CompositIdentifierOperationalTypeDto extends RepresentationModel {
    private String objectType;
    private CompanyDto company;
    private String core;
    private String variant;
    private int timetableYear;
    private Date startDate;
}
