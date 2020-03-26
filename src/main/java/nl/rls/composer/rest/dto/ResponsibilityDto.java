package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ResponsibilityDto extends IdentifiableRepresentationModel<ResponsibilityDto> {
    private CompanyDto responsibleRU;
    private CompanyDto responsibleIM;

}
