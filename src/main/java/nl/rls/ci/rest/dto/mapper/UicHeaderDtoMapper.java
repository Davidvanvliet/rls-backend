package nl.rls.ci.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;

import nl.rls.ci.controller.UicRequestController;
import nl.rls.ci.domain.UicHeader;
import nl.rls.ci.rest.dto.UicHeaderDto;

public class UicHeaderDtoMapper {
	public static UicHeaderDto map(UicHeader entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		UicHeaderDto dto = mapper.map(entity, UicHeaderDto.class);
		dto.add(linkTo(methodOn(UicRequestController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

}
