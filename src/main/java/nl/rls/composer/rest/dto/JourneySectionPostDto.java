package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JourneySectionPostDto {
	private String journeySectionOriginUrl;
	private String journeySectionDestinationUrl;
    private Integer livestockOrPeopleIndicator;
	private int trainMaxSpeed;
	private int maxAxleWeight;
	private String brakeType;
	private int brakeWeight;
}
