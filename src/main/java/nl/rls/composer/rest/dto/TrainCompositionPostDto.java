package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionPostDto {
    @Min(0)
    @Max(999999)
    private Integer livestockOrPeopleIndicator;
    private int trainMaxSpeed;
    private int maxAxleWeight;
    private String brakeType;
    @Min(1)
    @Max(99999)
    private int brakeWeight;

}
