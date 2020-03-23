package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionDto extends RepresentationModel {
    private List<TractionInTrainDto> tractions = new ArrayList<TractionInTrainDto>();
    private List<WagonInTrainDto> wagons = new ArrayList<WagonInTrainDto>();
    private Integer exceptionalGaugingIndicator;
    @Min(0)
    @Max(1)
    private Integer livestockOrPeopleIndicator;
    private Integer dangerousGoodsIndicator;
    private int trainType;
    private int trainWeight;
    private int trainLength;
    // private List<TrainCC_System> trainCCSystem;
    // private TrainRadioSystemDto trainRadioSystem;
    private int trainMaxSpeed;
    private int maxAxleWeight;
    private String brakeType;
    @Min(1)
    @Max(99999)
    private int brakeWeight;
    private int numberOfVehicles;
    private int numberOfAxles;

}
