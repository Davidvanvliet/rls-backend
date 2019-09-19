package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LocomotiveDto extends ResourceSupport {
	protected TractionTypeDto tractionType;
	protected LocoTypeNumberDto locoTypeNumber;
	protected String locoNumber;
	protected TractionModeDto tractionMode;
	protected Integer driverIndication;
	protected Integer tractionPositionInTrain;
}
