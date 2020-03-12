package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.DangerLabelController;
import nl.rls.composer.domain.code.DangerLabel;
import nl.rls.composer.rest.dto.DangerLabelDto;
import org.dozer.DozerBeanMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DangerLabelDtoMapper {
    public static DangerLabelDto map(DangerLabel dangerLabel) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        DangerLabelDto dangerLabelDto = mapper.map(dangerLabel, DangerLabelDto.class);
        dangerLabelDto.add(linkTo(methodOn(DangerLabelController.class).getById(dangerLabel.getId())).withSelfRel());
        return dangerLabelDto;
    }

}
