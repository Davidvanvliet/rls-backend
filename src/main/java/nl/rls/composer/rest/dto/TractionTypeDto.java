package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class TractionTypeDto extends IdentifiableRepresentationModel<TractionTypeDto> {
    private String code;
    private String description;
}
