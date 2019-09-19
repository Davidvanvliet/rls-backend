package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.TrainCompositionJourneySectionController;
import nl.rls.composer.domain.Locomotive;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.rest.dto.LocomotiveDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionPostDto;
import nl.rls.composer.rest.dto.WagonDto;

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

		List<LocomotiveDto> locomotiveDtoList = new ArrayList<LocomotiveDto>();
		for (Locomotive listItem : entity.getLocomotives()) {
			locomotiveDtoList.add(LocomotiveDtoMapper.map(listItem));
		}
		dto.setLocomotives(locomotiveDtoList);

		List<WagonDto> wagonDtoList = new ArrayList<WagonDto>();
		for (Wagon listItem : entity.getWagons()) {
			wagonDtoList.add(WagonDtoMapper.map(listItem));
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
