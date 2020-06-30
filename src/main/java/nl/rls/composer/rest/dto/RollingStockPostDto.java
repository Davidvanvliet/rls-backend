package nl.rls.composer.rest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "stockType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TractionInTrainPostDto.class, name = "traction"),
        @JsonSubTypes.Type(value = WagonInTrainPostDto.class, name = "wagon")
})
public class RollingStockPostDto {
    private String stockType;

    private Long stockIdentifier;
}
