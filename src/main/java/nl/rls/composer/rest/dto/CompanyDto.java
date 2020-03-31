package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class CompanyDto extends IdentifiableRepresentationModel<CompanyDto> {
    private String code;
    private String name;
    private String shortName;
    private String url;
    private String countryIso;
}
