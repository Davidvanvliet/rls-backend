package nl.rls.ci.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;

import nl.rls.ci.controller.UicRequestController;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.rest.dto.UicRequestDto;

public class UicRequestDtoMapper {
	public static UicRequestDto map(UicRequest entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		UicRequestDto dto = mapper.map(entity, UicRequestDto.class);
		dto.add(linkTo(methodOn(UicRequestController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

}
