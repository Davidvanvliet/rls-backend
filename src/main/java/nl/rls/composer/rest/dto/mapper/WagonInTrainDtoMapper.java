package nl.rls.composer.rest.dto.mapper;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.TrainCompositionJourneySectionController;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.WagonInTrainDto;

public class WagonInTrainDtoMapper {

	public static WagonInTrainDto map(WagonInTrain entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(WagonInTrain.class, WagonInTrainDto.class)
				.fields("wagon", "wagon",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.WagonConverter"))
				;
			}
		};
		mapper.addMapping(mappingBuilder);		
		WagonInTrainDto dto = mapper.map(entity, WagonInTrainDto.class);
		dto.add(linkTo(methodOn(TrainCompositionJourneySectionController.class).getWagonInTrain(entity.getTrainCompositionJourneySection().getId(), entity.getId())).withSelfRel());
		return dto;
	}

}
