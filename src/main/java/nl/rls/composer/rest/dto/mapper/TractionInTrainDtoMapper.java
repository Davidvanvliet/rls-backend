package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.TractionInTrainController;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.rest.dto.TractionInTrainDto;

public class TractionInTrainDtoMapper {

	public static TractionInTrainDto map(TractionInTrain entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(TractionInTrain.class, TractionInTrainDto.class)
				.fields("traction", "traction",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.TractionConverter"))
				;
			}
		};
		mapper.addMapping(mappingBuilder);		
		TractionInTrainDto dto = mapper.map(entity, TractionInTrainDto.class);
		dto.add(linkTo(methodOn(TractionInTrainController.class).getTractionInTrain(entity.getTrainCompositionJourneySection().getId(), entity.getId())).withSelfRel());
		return dto;
	}

}
