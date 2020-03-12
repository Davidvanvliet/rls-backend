package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.DangerGoodsInWagonController;
import nl.rls.composer.domain.DangerGoodsInWagon;
import nl.rls.composer.rest.dto.DangerGoodsInWagonDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DangerGoodsInWagonDtoMapper {

    public static DangerGoodsInWagonDto map(DangerGoodsInWagon entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(DangerGoodsInWagon.class, DangerGoodsInWagonDto.class).fields("dangerGoodsType", "dangerGoodsType",
                        FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.DangerGoodsTypeConverter"));

            }
        };
        mapper.addMapping(mappingBuilder);
        DangerGoodsInWagonDto dto = mapper.map(entity, DangerGoodsInWagonDto.class);
        dto.add(linkTo(methodOn(DangerGoodsInWagonController.class).getDangerGoodsInWagon(entity.getWagonInTrain().getId(), entity.getId())).withSelfRel());
        return dto;
    }

}
