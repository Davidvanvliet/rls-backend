package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

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
public class CompositIdentifierOperationalTypeDto extends IdentifiableRepresentationModel<CompositIdentifierOperationalTypeDto> {
    private String objectType;
    private CompanyDto company;
    private String core;
    private String variant;
    private int timetableYear;
    private Date startDate;
}
