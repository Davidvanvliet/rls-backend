package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class JourneySectionDto extends ResourceSupport {
    //    private LocationIdentDto journeySectionOrigin;
//    private LocationIdentDto journeySectionDestination;
//    private CompanyDto responsibilityActualSectionIM;
//    private CompanyDto responsibilityNextSectionIM;
//    private CompanyDto responsibilityActualSectionRU;
//    private CompanyDto responsibilityNextSectionRU;
    private TrainCompositionDto trainComposition;
    private List<TrainActivityTypeDto> activities = new ArrayList<TrainActivityTypeDto>();
}
