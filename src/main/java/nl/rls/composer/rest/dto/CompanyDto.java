package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CompanyDto extends ResourceSupport {
	private String code;
	private String name;
	private String shortName;
	private String url;
	private String countryIso;
}
