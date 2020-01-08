package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainDto extends ResourceSupport {
	private int driverIndication;
	private int position;
    private TractionDto locomotive;
}
