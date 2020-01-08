package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TractionInTrainAddDto extends ResourceSupport {
	private int position;
	private int driverIndication;
	private String traction;
}
