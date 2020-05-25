package nl.rls.composer.rest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = Id.NAME, include = As.EXISTING_PROPERTY, property = "stockType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TractionInTrainDto.class, name = "traction"),
        @JsonSubTypes.Type(value = WagonInTrainDto.class, name = "wagon")
})
public class RollingStockDTO extends IdentifiableRepresentationModel<RollingStockDTO> {
    private int position;

    private String stockType;

    private Long stockIdentifier;

    private boolean isGaugedExceptional;

    private boolean containsDangerousGoods;

    private int totalWeight;

    private int loadWeight;

    private int length;

    private int maxAxleWeight;

    private int maxSpeed;
}
