package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionDto extends IdentifiableRepresentationModel<TrainCompositionDto> {
    private List<RollingStockDto> rollingStock;
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
