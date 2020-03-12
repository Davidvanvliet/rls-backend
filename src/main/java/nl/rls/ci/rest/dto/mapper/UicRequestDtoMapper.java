package nl.rls.ci.rest.dto.mapper;

import nl.rls.ci.controller.UicRequestController;
import nl.rls.ci.controller.XmlMessageController;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.rest.dto.UicRequestDto;
import org.dozer.DozerBeanMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class UicRequestDtoMapper {
    public static UicRequestDto map(UicRequest entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        UicRequestDto dto = mapper.map(entity, UicRequestDto.class);
        dto.add(linkTo(methodOn(XmlMessageController.class).getById(entity.getMessage().getId())).withRel("xmlmessage"));
        dto.add(linkTo(methodOn(UicRequestController.class).getById(entity.getId())).withSelfRel());
        return dto;
    }

}
