package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainDto extends RepresentationModel {
    private int driverIndication;
    private int position;
    private TractionDto traction;
}
