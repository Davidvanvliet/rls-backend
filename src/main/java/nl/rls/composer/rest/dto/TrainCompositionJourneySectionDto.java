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
    private List<TractionInTrainDto> tractions  = new ArrayList<TractionInTrainDto>();;
    private List<WagonInTrainDto> wagons  = new ArrayList<WagonInTrainDto>();
    private List<TrainActivityTypeDto> activities  = new ArrayList<TrainActivityTypeDto>();
    private Integer exceptionalGaugingInd;
    private Integer livestockOrPeopleIndicator;
    private Integer dangerousGoodsIndicator;
	private int trainType;
	private int trainWeight;
	private int trainLength;
	// private List<TrainCC_System> trainCCSystem;
	// private TrainRadioSystemDto trainRadioSystem;
	private int trainMaxSpeed;
	private int maxAxleWeight;
	private String brakeType;
	private int brakeWeight;
	private int numberOfVehicles;
	private int numberOfAxles;

}
