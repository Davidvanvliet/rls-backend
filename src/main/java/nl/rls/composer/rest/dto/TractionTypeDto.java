package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@NoArgsConstructor
@Getter
@Setter
public class TractionTypeDto extends ResourceSupport {
	private String code;
	private String description;
}
