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
	private TrainDto train;
}
