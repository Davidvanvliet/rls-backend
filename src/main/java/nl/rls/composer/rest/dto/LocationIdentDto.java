package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter @Setter
public class LocationIdentDto extends ResourceSupport {
    private int locationPrimaryCode;
    private String countryIso;
    private String primaryLocationName;
    private String code;
    private String latitude;
    private String longitude;
}
