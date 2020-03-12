package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.TrainActivityTypeController;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TrainActivityTypeDtoMapper {
    public static TrainActivityTypeDto map(TrainActivityType trainActivityType) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        TrainActivityTypeDto trainActivityTypeDto = mapper.map(trainActivityType, TrainActivityTypeDto.class);
        trainActivityTypeDto.add(linkTo(methodOn(TrainActivityTypeController.class).getById(trainActivityType.getId())).withSelfRel());
        return trainActivityTypeDto;
    }


}
