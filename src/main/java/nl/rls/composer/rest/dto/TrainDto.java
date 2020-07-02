package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.ci.domain.dto.CustomMessageStatusDto;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TrainDto extends IdentifiableRepresentationModel<TrainDto> {
    @Size(min = 1, max = 8)
    private String operationalTrainNumber;
    private String transferPoint;
    private Date scheduledTimeAtHandover;
    private Date scheduledDateTimeAtTransfer;
    private Integer trainType;
    private List<JourneySectionDto> journeySections = new ArrayList<>();
    private List<CustomMessageStatusDto> customMessageStatuses;
}
