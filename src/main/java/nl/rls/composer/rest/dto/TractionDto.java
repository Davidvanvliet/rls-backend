package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class TractionDto extends RepresentationModel {
    private TractionTypeDto tractionType;
    private String locoTypeNumber;
    private String locoNumber;
    private TractionModeDto tractionMode;
    private String type;
    private int lengthOverBuffers;
    private int numberOfAxles;
    private int weight;
    private int brakeWeight;
}
