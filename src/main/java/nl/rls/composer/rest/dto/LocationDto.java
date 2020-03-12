package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class LocationDto extends RepresentationModel {
    private int locationPrimaryCode;
    private String countryCodeIso;
    private String primaryLocationName;
    private String code;
    private String latitude;
    private String longitude;
}
