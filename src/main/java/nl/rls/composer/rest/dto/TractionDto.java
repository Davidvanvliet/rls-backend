package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TractionDto extends ResourceSupport {
	protected TractionTypeDto tractionType;
	protected String locoTypeNumber;
	protected String locoNumber;
	protected TractionModeDto tractionMode;
}
