package nl.rls.composer.rest.dto.mapper;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.WagonTechDataController;
import nl.rls.composer.domain.WagonTechData;
import nl.rls.composer.rest.dto.WagonTechDataDto;

public class WagonTechDataDtoMapper {

	public static WagonTechDataDto map(WagonTechData entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		WagonTechDataDto dto = mapper.map(entity, WagonTechDataDto.class);
	    dto.add(linkTo(methodOn(WagonTechDataController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

}
