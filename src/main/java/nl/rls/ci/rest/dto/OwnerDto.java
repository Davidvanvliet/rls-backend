package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class OwnerDto extends IdentifiableRepresentationModel<OwnerDto> {
    private String code;
    private String name;

}
