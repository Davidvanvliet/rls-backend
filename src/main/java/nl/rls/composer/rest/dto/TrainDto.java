package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TrainDto extends RepresentationModel {
    @Size(min = 1, max = 8)
    private String operationalTrainNumber;
    private String transferPoint;
    private Date scheduledTimeAtHandover;
    private Date scheduledDateTimeAtTransfer;
    private List<JourneySectionDto> journeySections = new ArrayList<>();
}
