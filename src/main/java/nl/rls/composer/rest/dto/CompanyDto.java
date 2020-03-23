package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class CompanyDto extends RepresentationModel {
    private String code;
    private String name;
    private String shortName;
    private String url;
    private String countryIso;
}
