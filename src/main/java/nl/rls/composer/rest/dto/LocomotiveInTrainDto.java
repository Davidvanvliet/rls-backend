package nl.rls.composer.rest.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocomotiveInTrainDto {
	private int driverIndication;
	private int tractionPositionInTrain;
    private LocomotiveDto locomotive;
}
