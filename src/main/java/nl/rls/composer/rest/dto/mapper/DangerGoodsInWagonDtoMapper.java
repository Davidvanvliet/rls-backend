package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.domain.DangerGoodsInWagon;
import nl.rls.composer.rest.dto.DangerGoodsInWagonDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

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
        int wagonInTrainId = entity.getWagonInTrain().getId();
        int dangerGoodsId = entity.getId();
//        Link link = linkTo(methodOn(DangerGoodsInWagonController.class).getDangerGoodsInWagon(wagonInTrainId, dangerGoodsId)).withSelfRel();
//        dto.add(link);
        return dto;
    }

}
