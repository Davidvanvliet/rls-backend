package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class ActivityInTrainDto extends IdentifiableRepresentationModel<ActivityInTrainDto> {
    private TrainActivityTypeDto trainActivityType;
}
