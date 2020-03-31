package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDto extends IdentifiableRepresentationModel<RoleDto> {
    private String name;
}
