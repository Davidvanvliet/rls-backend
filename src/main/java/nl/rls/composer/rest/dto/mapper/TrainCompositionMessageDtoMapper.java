package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.CompanyController;
import nl.rls.composer.controller.LocationIdentController;
import nl.rls.composer.controller.TrainCompositionMessageController;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.TrainCompositionMessage;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionMessageCreateDto;
import nl.rls.composer.rest.dto.TrainCompositionMessageDto;

public class TrainCompositionMessageDtoMapper {
	public static TrainCompositionMessageDto map(TrainCompositionMessage entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(TrainCompositionMessage.class, TrainCompositionMessageDto.class)
				.fields("sender.code", "sender")
				.fields("recipient.code", "recipient")
				;
			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionMessageDto dto = mapper.map(entity, TrainCompositionMessageDto.class);
		List<TrainCompositionJourneySectionDto> dtoList = new ArrayList<TrainCompositionJourneySectionDto>();
		for (TrainCompositionJourneySection listItem : entity.getTrainCompositionJourneySection()) {
			dtoList.add(TrainCompositionJourneySectionDtoMapper.map(listItem));
		}
		dto.setTrainCompositionJourneySection(dtoList);
		
		dto.add(linkTo(methodOn(TrainCompositionMessageController.class).getById(entity.getId())).withSelfRel());
		dto.add(linkTo(methodOn(CompanyController.class).getById(entity.getSender().getId())).withRel("sender").withTitle(entity.getSender().getName()));
		dto.add(linkTo(methodOn(CompanyController.class).getById(entity.getRecipient().getId())).withRel("recipient").withTitle(entity.getRecipient().getName()));
		dto.add(linkTo(methodOn(CompanyController.class).getById(entity.getTransfereeIM().getId())).withRel("transfereeIM").withTitle(entity.getTransfereeIM().getName()));
		dto.add(linkTo(methodOn(LocationIdentController.class).getById(entity.getTransferPoint().getLocationPrimaryCode())).withRel("transferPoint").withTitle(entity.getTransferPoint().getPrimaryLocationName()));
		return dto;
	}

	public static TrainCompositionMessage map(TrainCompositionMessageCreateDto dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionMessage trainCompositionMessage = mapper.map(dto, TrainCompositionMessage.class);
		return trainCompositionMessage;
	}

}
