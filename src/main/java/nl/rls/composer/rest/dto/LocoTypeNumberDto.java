package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class LocoTypeNumberDto extends RepresentationModel {
    private String typeCode1;
    private String typeCode2;
    private String countryCode;
    private String seriesNumber;
    private String serialNumber;
    private String controlDigit;
}
