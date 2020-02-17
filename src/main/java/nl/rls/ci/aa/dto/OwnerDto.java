package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OwnerDto extends ResourceSupport {
    private String code;
    private String name;
    private List<LicenseDto> licenses;

}
