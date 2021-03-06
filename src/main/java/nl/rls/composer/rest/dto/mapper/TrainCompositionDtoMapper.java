package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.RollingStockController;
import nl.rls.composer.controller.TrainCompositionController;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.RollingStock;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.RollingStockDto;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.dozer.loader.api.TypeMappingOptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TrainCompositionDtoMapper {

    public static TrainCompositionDto map(TrainComposition entity) {
        if (entity == null) {
            return null;
        }
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(TrainComposition.class, TrainCompositionDto.class, TypeMappingOptions.oneWay(), TypeMappingOptions.mapNull(false));

            }
        };
        mapper.addMapping(mappingBuilder);

        TrainCompositionDto dto = mapper.map(entity, TrainCompositionDto.class);

        List<RollingStockDto> rollingStockDtos = new ArrayList<>();
        for (RollingStock rollingStock : entity.getRollingStock()) {
            RollingStockDto rollingStockDto = RollingStockDtoMapper.map(rollingStock);
            rollingStockDtos.add(rollingStockDto);
        }
        dto.setRollingStock(rollingStockDtos);

        dto.add(linkTo(methodOn(RollingStockController.class).getRollingStockByTrainCompositionId(entity.getId())).withRel("rollingStock"));
        dto.add(linkTo(methodOn(TrainCompositionController.class).getById(entity.getId())).withSelfRel());
        return dto;
    }

    public static JourneySection map(JourneySectionPostDto dto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        JourneySection entity = mapper.map(dto, JourneySection.class);
        return entity;
    }

}
