package nl.rls.composer.rest.dto.mapper;

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
                mapping(Wagon.class, WagonDto.class)
                        .fields("wagonWeightEmpty", "weightEmpty")
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        WagonDto wagonDto = mapper.map(wagon, WagonDto.class);
        wagonDto.add(linkTo(methodOn(WagonController.class).getById(wagon.getId())).withSelfRel());
        return wagonDto;
    }

    public static Wagon map(WagonDto wagon) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(WagonDto.class, Wagon.class)
                        .fields("wagonWeightEmpty", "weightEmpty");
            }
        };
        mapper.addMapping(mappingBuilder);
        return mapper.map(wagon, Wagon.class);
    }

}
