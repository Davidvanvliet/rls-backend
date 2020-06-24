package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.rest.dto.TractionInTrainDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

public class TractionInTrainDtoMapper {

    public static TractionInTrainDto map(TractionInTrain entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(TractionInTrain.class, TractionInTrainDto.class)
                        .fields("traction", "traction",
                                FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.TractionConverter"))
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        TractionInTrainDto dto = mapper.map(entity, TractionInTrainDto.class);
//        dto.add(linkTo(methodOn(TractionInTrainController.class).getTractionInTrain(entity.getTrainComposition().getId(), entity.getId())).withSelfRel());
        return dto;
    }

    public static TractionInTrain map(TractionInTrainDto dto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(TractionInTrainDto.class, TractionInTrain.class)
                        .fields("traction", "traction",
                                FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.TractionConverter"))
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        return mapper.map(dto, TractionInTrain.class);

    }

}
