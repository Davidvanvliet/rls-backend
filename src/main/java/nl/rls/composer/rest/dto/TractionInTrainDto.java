package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainDto extends RepresentationModel {
    private int driverIndication;
    private int position;
    private TractionDto traction;
}
