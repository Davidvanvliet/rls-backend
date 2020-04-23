package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class JourneySectionPostDto {
    private String journeySectionOriginUrl;
    private String journeySectionDestinationUrl;
    @Min(0)
    @Max(1)
    private Integer livestockOrPeopleIndicator;
    private String brakeType;
    private List<ActivityInTrainAddDto> activities;
}
