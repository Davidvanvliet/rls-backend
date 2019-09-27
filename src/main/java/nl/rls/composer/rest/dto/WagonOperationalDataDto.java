package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.BrakeType;

@NoArgsConstructor
@Getter
@Setter
public class WagonOperationalDataDto {
	private BrakeType brakeType;
	private int totalLoadWeight;
	private int brakeWeight;
	private int wagonMaxSpeed;

}
