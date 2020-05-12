package nl.rls.composer.rest.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionDto extends IdentifiableRepresentationModel<TrainCompositionDto> {
    private List<TractionInTrainDto> tractions = new ArrayList<TractionInTrainDto>();
    private List<WagonInTrainDto> wagons = new ArrayList<WagonInTrainDto>();
    private Integer exceptionalGaugingIndicator;
    private Integer livestockOrPeopleIndicator;
    private Integer dangerousGoodsIndicator;
    private int trainWeight;
    private int trainLength;
    // private List<TrainCC_System> trainCCSystem;
    // private TrainRadioSystemDto trainRadioSystem;
    private int trainMaxSpeed;
    private int maxAxleWeight;
    private String brakeType;
    private int brakeWeight;
    private int numberOfVehicles;
    private int numberOfAxles;

}
