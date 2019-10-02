package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.TrainRunningDataController;
import nl.rls.composer.domain.ActivityInTrain;
import nl.rls.composer.domain.TrainRunningData;
import nl.rls.composer.rest.dto.ActivityInTrainDto;
import nl.rls.composer.rest.dto.TrainRunningDataDto;

public class TrainRunningDataDtoMapper {
	public static TrainRunningDataDto map(TrainRunningData entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};

		mapper.addMapping(mappingBuilder);
		TrainRunningDataDto dto = mapper.map(entity, TrainRunningDataDto.class);

		List<ActivityInTrainDto> dtoList = new ArrayList<ActivityInTrainDto>();
		for (ActivityInTrain activityInTrain : entity.getActivities()) {
			ActivityInTrainDto activityInTrainDto = ActivityInTrainDtoMapper.map(activityInTrain);
			dtoList.add(activityInTrainDto);
		}
		dto.setActivities(dtoList);

		dto.add(linkTo(methodOn(TrainRunningDataController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

}
