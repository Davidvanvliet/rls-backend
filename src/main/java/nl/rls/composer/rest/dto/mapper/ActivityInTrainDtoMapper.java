package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.LocationIdentController;
import nl.rls.composer.controller.TrainActivityTypeController;
import nl.rls.composer.controller.TrainRunningDataController;
import nl.rls.composer.rest.dto.ActivityInTrainDto;

public class ActivityInTrainDtoMapper {
	public static ActivityInTrainDto map(nl.rls.composer.domain.ActivityInTrain entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		ActivityInTrainDto dto = mapper.map(entity, ActivityInTrainDto.class);
		dto.add(linkTo(methodOn(TrainActivityTypeController.class).getById(entity.getTrainActivityType().getId())).withRel("trainActivityType").withTitle(entity.getTrainActivityType().getDescription()));
		dto.add(linkTo(methodOn(LocationIdentController.class).getById(entity.getActivityLocationIdent().getLocationPrimaryCode())).withRel("activityLocationIdent").withTitle(entity.getActivityLocationIdent().getPrimaryLocationName()));
		dto.add(linkTo(methodOn(TrainRunningDataController.class).getActivityInTrain(entity.getTrainRunningData().getId(), entity.getId())).withSelfRel());
		return dto;
	}
}
