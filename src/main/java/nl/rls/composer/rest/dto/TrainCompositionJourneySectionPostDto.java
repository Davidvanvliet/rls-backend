package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TrainCompositionJourneySectionPostDto {
	private String journeySectionOrigin;
	private String journeySectionDestination;
	private Integer trainType;
    private Integer exceptionalGaugingInd;
    private Integer livestockOrPeopleIndicator;
    private Integer dangerousGoodsIndicator;
}
