package nl.rls.composer.rest.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionMessageDto extends GenericMessageDto  {
	private List<CompositIdentifierOperationalTypeDto> compositIdentifierOperationalType = new ArrayList<CompositIdentifierOperationalTypeDto>();
	private OperationalTrainNumberIdentifierDto operationalTrainNumberIdentifier;
	private String operationalTrainNumber;
	private LocationIdentDto transferPoint;
	private CompanyDto transfereeIM;
	private List<TrainCompositionJourneySectionDto> trainCompositionJourneySection = new ArrayList<TrainCompositionJourneySectionDto>();
}
