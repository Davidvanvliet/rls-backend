package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocoTypeNumberDto extends ResourceSupport {
	private String typeCode1;
	private String typeCode2;
	private String countryCode;
	private String seriesNumber;
	private String serialNumber;
	private String controlDigit;
}
