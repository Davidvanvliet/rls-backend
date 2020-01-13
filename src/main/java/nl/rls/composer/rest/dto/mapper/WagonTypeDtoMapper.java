package nl.rls.composer.rest.dto.mapper;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.WagonTypeController;
import nl.rls.composer.domain.WagonType;
import nl.rls.composer.rest.dto.WagonTypeDto;

public class WagonTypeDtoMapper {

	public static WagonTypeDto map(WagonType entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		WagonTypeDto dto = mapper.map(entity, WagonTypeDto.class);
	    dto.add(linkTo(methodOn(WagonTypeController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

}
