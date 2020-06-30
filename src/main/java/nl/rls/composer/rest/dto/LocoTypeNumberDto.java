package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class LocoTypeNumberDto extends IdentifiableRepresentationModel<LocoTypeNumberDto> {
    private String typeCode1;
    private String typeCode2;
    private String countryCode;
    private String seriesNumber;
    private String serialNumber;
    private String controlDigit;
}
