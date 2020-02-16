package nl.rls.ci.aa.dto;

import java.util.List;

import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OwnerDto extends ResourceSupport {
	private String code;
	private String name;
	private List<LicenseDto> licenses;

}
