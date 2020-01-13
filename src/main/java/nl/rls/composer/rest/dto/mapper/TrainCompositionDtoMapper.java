package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.TractionInTrainController;
import nl.rls.composer.controller.TrainCompositionController;
import nl.rls.composer.controller.WagonInTrainController;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.TractionInTrainDto;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;

public class TrainCompositionDtoMapper {

	public static TrainCompositionDto map(TrainComposition entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionDto dto = mapper.map(entity, TrainCompositionDto.class);

		List<TractionInTrainDto> dtoList = new ArrayList<TractionInTrainDto>();
		for (TractionInTrain tractionInTrain : entity.getTractions()) {
			TractionInTrainDto tractionInTrainDto = new TractionInTrainDto();
			tractionInTrainDto.setLocomotive(TractionDtoMapper.map(tractionInTrain.getTraction()));
			tractionInTrainDto.setDriverIndication(tractionInTrain.getDriverIndication());
			tractionInTrainDto.setPosition(tractionInTrain.getPosition());
			dtoList.add(tractionInTrainDto);
		}
		dto.setTractions(dtoList);

		List<WagonInTrainDto> wagonDtoList = new ArrayList<WagonInTrainDto>();
		System.out.println("entity.getWagons(): "+entity.getWagons());
		for (WagonInTrain wagonInTrain : entity.getWagons()) {
			WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
			wagonDtoList.add(wagonInTrainDto);
		}
		dto.setWagons(wagonDtoList);
		
		dto.add(linkTo(methodOn(WagonInTrainController.class).getAllWagonInTrain(entity.getId())).withRel("wagons"));
		dto.add(linkTo(methodOn(TractionInTrainController.class).getAllTractionInTrain(entity.getId())).withRel("tractions"));
		dto.add(linkTo(methodOn(TrainCompositionController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

	public static JourneySection map(JourneySectionPostDto dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		JourneySection entity = mapper.map(dto, JourneySection.class);
		return entity;
	}

}
