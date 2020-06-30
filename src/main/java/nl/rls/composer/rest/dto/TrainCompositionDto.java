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
    private Integer livestockOrPeopleIndicator;
    private int weight;
    private int length;
    private boolean gaugedExceptional;
    private boolean containsDangerousGoods;
    // private List<TrainCC_System> trainCCSystem;
    // private TrainRadioSystemDto trainRadioSystem;
    private int maxSpeed;
    private int maxAxleWeight;
    private String brakeType;
    private int brakeWeight;
    private int numberOfVehicles;
    private int numberOfAxles;

}
