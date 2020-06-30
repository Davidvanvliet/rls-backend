package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class TractionDto extends IdentifiableRepresentationModel<TractionDto> {
    private TractionTypeDto tractionType;
    private String locoTypeNumber;
    private String locoNumber;
    private String typeName;
    private String code;
    private int lengthOverBuffers;
    private int numberOfAxles;
    private int weight;
    private int brakeWeightG;
    private int brakeWeightP;
}
