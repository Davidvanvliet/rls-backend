package nl.rls.composer.rest.dto.mapper;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.WagonInTrainController;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.WagonInTrainDto;

public class WagonInTrainDtoMapper {

	public static WagonInTrainDto map(WagonInTrain entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(WagonInTrain.class, WagonInTrainDto.class)
				.fields("wagonLoad", "wagonLoad",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.WagonLoadConverter"))
				;
			}
		};
		mapper.addMapping(mappingBuilder);		
		WagonInTrainDto dto = mapper.map(entity, WagonInTrainDto.class);
		dto.add(linkTo(methodOn(WagonInTrainController.class).getWagonInTrain(entity.getTrainCompositionJourneySection().getId(), entity.getId())).withSelfRel());
		return dto;
	}

}
