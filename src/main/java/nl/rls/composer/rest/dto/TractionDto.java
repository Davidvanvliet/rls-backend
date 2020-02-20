package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@NoArgsConstructor
@Getter
@Setter
public class TractionDto extends ResourceSupport {
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
