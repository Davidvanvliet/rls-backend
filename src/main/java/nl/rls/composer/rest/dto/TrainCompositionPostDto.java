package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionPostDto {
    private Integer livestockOrPeopleIndicator;
	private int trainMaxSpeed;
	private int maxAxleWeight;
	private String brakeType;
	private int brakeWeight;

}
