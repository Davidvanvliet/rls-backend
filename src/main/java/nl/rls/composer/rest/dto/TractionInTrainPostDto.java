package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainPostDto extends RepresentationModel {
    private int position;
    private int driverIndication;
    private String tractionUrl;
}
