package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.DangerGoodsTypeController;
import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.rest.dto.DangerGoodsTypeDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DangerGoodsTypeDtoMapper {

    public static DangerGoodsTypeDto map(DangerGoodsType entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        DangerGoodsTypeDto dto = mapper.map(entity, DangerGoodsTypeDto.class);
        dto.add(linkTo(methodOn(DangerGoodsTypeController.class).getById(entity.getId())).withSelfRel());
        return dto;
    }

}
