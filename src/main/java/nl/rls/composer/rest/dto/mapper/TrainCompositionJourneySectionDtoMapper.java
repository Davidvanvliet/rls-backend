package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.TrainCompositionJourneySectionController;
import nl.rls.composer.domain.LocomotiveInTrain;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.LocomotiveInTrainDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionPostDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;

public class TrainCompositionJourneySectionDtoMapper {

	public static TrainCompositionJourneySectionDto map(TrainCompositionJourneySection entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(TrainCompositionJourneySection.class, TrainCompositionJourneySectionDto.class)
				.fields("journeySection.responsibilityNextSection.responsibleIM", "responsibilityNextSectionIM",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.CompanyConverter"))
				.fields("journeySection.responsibilityNextSection.responsibleRU", "responsibilityNextSectionRU",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.CompanyConverter"))
				.fields("journeySection.responsibilityActualSection.responsibleIM", "responsibilityActualSectionIM",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.CompanyConverter"))
				.fields("journeySection.responsibilityActualSection.responsibleRU", "responsibilityActualSectionRU",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.CompanyConverter"))
				.fields("journeySection.journeySectionOrigin", "journeySectionOrigin",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.LocationIdentConverter"))
				.fields("journeySection.journeySectionDestination", "journeySectionDestination", 
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.LocationIdentConverter"))
				;
			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionJourneySectionDto dto = mapper.map(entity, TrainCompositionJourneySectionDto.class);

		List<LocomotiveInTrainDto> locomotiveDtoList = new ArrayList<LocomotiveInTrainDto>();
		for (LocomotiveInTrain locomotiveInTrain : entity.getLocomotives()) {
			LocomotiveInTrainDto locomotiveInTrainDto = new LocomotiveInTrainDto();
			locomotiveInTrainDto.setLocomotive(LocomotiveDtoMapper.map(locomotiveInTrain.getLocomotive()));
			locomotiveInTrainDto.setDriverIndication(locomotiveInTrain.getDriverIndication());
			locomotiveInTrainDto.setTractionPositionInTrain(locomotiveInTrain.getTractionPositionInTrain());
			locomotiveDtoList.add(locomotiveInTrainDto);
		}
		dto.setLocomotives(locomotiveDtoList);

		List<WagonInTrainDto> wagonDtoList = new ArrayList<WagonInTrainDto>();
		System.out.println("entity.getWagons(): "+entity.getWagons());
		for (WagonInTrain wagonInTrain : entity.getWagons()) {
			WagonInTrainDto wagonInTrainDto = new WagonInTrainDto();
			wagonInTrainDto.setWagon(WagonDtoMapper.map(wagonInTrain.getWagon()));
			wagonInTrainDto.setWagonTrainPosition(wagonInTrain.getWagonTrainPosition());
			wagonDtoList.add(wagonInTrainDto);
		}
		dto.setWagons(wagonDtoList);

		dto.add(linkTo(methodOn(TrainCompositionJourneySectionController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

	public static TrainCompositionJourneySection map(TrainCompositionJourneySectionPostDto dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionJourneySection entity = mapper.map(dto, TrainCompositionJourneySection.class);
		return entity;
	}

}
