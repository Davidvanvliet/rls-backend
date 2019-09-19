package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ResponsibilityDto extends ResourceSupport {
    private CompanyDto responsibleRU;
    private CompanyDto responsibleIM;

}
