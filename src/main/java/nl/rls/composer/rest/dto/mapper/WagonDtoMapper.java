package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.WagonController;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.rest.dto.WagonDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class WagonDtoMapper {

    public static WagonDto map(Wagon wagon) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        WagonDto wagonDto = mapper.map(wagon, WagonDto.class);
        wagonDto.add(linkTo(methodOn(WagonController.class).getById(wagon.getId())).withSelfRel());
        return wagonDto;
    }

}
