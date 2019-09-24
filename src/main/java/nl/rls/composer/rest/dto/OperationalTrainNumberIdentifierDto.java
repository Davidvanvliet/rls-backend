package nl.rls.composer.rest.dto;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OperationalTrainNumberIdentifierDto extends ResourceSupport {
	private String operationalTrainNumber;
	private Date scheduledTimeAtHandover;
	private Date scheduledDateTimeAtTransfer;

}
