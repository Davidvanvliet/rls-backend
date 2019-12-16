package nl.rls.composer.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TrainDto extends ResourceSupport   {
    private String operationalTrainNumber;
	private String transferPoint;
    private Date scheduledTimeAtHandover;
    private Date scheduledDateTimeAtTransfer;
	private List<TrainCompositionJourneySectionDto> trainCompositionJourneySection = new ArrayList<TrainCompositionJourneySectionDto>();
}
