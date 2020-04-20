package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.TractionController;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.rest.dto.TractionCreateDto;
import nl.rls.composer.rest.dto.TractionDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TractionDtoMapper {

    public static TractionDto map(Traction entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
//              mapping(TractionDto.class, Traction.class)
//              .fields("tractionType", "tractionType", FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.TractionTypeConverter"));
            }
        };
        mapper.addMapping(mappingBuilder);
        TractionDto dto = mapper.map(entity, TractionDto.class);
        dto.add(linkTo(methodOn(TractionController.class).getById(entity.getId())).withSelfRel());
        return dto;
    }

    public static Traction map(TractionCreateDto dto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
//                mapping(TractionCreateDto.class, Traction.class)
//                        .fields("tractionType", "tractionType", FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.TractionTypeConverter"));
            }
        };
        mapper.addMapping(mappingBuilder);
        Traction entity = mapper.map(dto, Traction.class);
        return entity;
    }

}
