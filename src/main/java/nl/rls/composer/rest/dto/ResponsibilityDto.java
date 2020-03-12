package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ResponsibilityDto extends RepresentationModel {
    private CompanyDto responsibleRU;
    private CompanyDto responsibleIM;

}
