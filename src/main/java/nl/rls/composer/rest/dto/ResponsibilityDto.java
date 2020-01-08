package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ResponsibilityDto extends ResourceSupport {
    private CompanyDto responsibleRU;
    private CompanyDto responsibleIM;

}
