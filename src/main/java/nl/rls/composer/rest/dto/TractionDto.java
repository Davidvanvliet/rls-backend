package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@NoArgsConstructor
@Getter
@Setter
public class TractionDto extends ResourceSupport {
	protected TractionTypeDto tractionType;
	protected String locoTypeNumber;
	protected String locoNumber;
	protected TractionModeDto tractionMode;
}
