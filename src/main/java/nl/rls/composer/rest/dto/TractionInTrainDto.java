package nl.rls.composer.rest.dto;
import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainDto extends ResourceSupport {
	private int driverIndication;
	private int tractionPositionInTrain;
    private TractionDto locomotive;
}
