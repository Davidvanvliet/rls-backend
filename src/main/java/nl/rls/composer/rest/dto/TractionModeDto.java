package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

/**
 * Identifies the mode of deployment of a traction within a train First digit –
 * traction role Second digit – position in group of traction units with the
 * same role
 */
@ToString
@NoArgsConstructor
@Getter
@Setter
public class TractionModeDto extends IdentifiableRepresentationModel<TractionModeDto> {
    private String code;
    private String description;

}
