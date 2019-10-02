package nl.rls.composer.rest.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionJourneySectionDto extends ResourceSupport {
//    private LocationIdentDto journeySectionOrigin;
//    private LocationIdentDto journeySectionDestination;
//    private CompanyDto responsibilityActualSectionIM;
//    private CompanyDto responsibilityNextSectionIM;
//    private CompanyDto responsibilityActualSectionRU;
//    private CompanyDto responsibilityNextSectionRU;
    private TrainRunningDataDto trainRunningData;
    private List<LocomotiveInTrainDto> locomotives  = new ArrayList<LocomotiveInTrainDto>();;
    private List<WagonInTrainDto> wagons  = new ArrayList<WagonInTrainDto>();
    private Integer exceptionalGaugingInd;
    private Integer livestockOrPeopleIndicator;
    private Integer dangerousGoodsIndicator;
}
