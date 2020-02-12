package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.CompanyController;
import nl.rls.composer.controller.TrainCompositionMessageController;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionMessageCreateDto;
import nl.rls.composer.rest.dto.TrainCompositionMessageDto;
import nl.rls.composer.rest.dto.TrainDto;

public class TrainCompositionMessageDtoMapper {
	public static TrainCompositionMessageDto map(TrainCompositionMessage entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(TrainCompositionMessage.class, TrainCompositionMessageDto.class)
				.fields("sender.code", "sender")
				.fields("recipient.code", "recipient")
				.fields("train", "train",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.TrainConverter"));
			}
		};
		mapper.addMapping(mappingBuilder);
		TrainCompositionMessageDto dto = mapper.map(entity, TrainCompositionMessageDto.class);

		dto.add(linkTo(methodOn(TrainCompositionMessageController.class).getById(entity.getId())).withSelfRel());
		dto.add(linkTo(methodOn(CompanyController.class).getById(entity.getSender().getId())).withRel("sender").withTitle(entity.getSender().getName()));
		dto.add(linkTo(methodOn(CompanyController.class).getById(entity.getRecipient().getId())).withRel("recipient").withTitle(entity.getRecipient().getName()));
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
