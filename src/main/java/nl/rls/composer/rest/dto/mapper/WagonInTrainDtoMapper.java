package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.WagonInTrainController;
import nl.rls.composer.domain.DangerGoodsInWagon;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.DangerGoodsInWagonDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class WagonInTrainDtoMapper {

    public static WagonInTrainDto map(WagonInTrain entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(WagonInTrain.class, WagonInTrainDto.class)
                        .fields("wagon", "wagon",
                                FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.WagonConverter"))
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        WagonInTrainDto dto = mapper.map(entity, WagonInTrainDto.class);

        List<DangerGoodsInWagonDto> dtoList = new ArrayList<DangerGoodsInWagonDto>();
        for (DangerGoodsInWagon dangerGoodsInWagon : entity.getDangerGoodsInWagons()) {
            System.out.println("dangerGoodsInWagon " + dangerGoodsInWagon.getId());
            DangerGoodsInWagonDto dangerGoodsInWagonDto = DangerGoodsInWagonDtoMapper.map(dangerGoodsInWagon);
            dtoList.add(dangerGoodsInWagonDto);
        }
        dto.setDangerGoods(dtoList);

        dto.add(linkTo(methodOn(WagonInTrainController.class).getWagonInTrain(entity.getTrainComposition().getId(), entity.getId())).withSelfRel());
        return dto;
    }

}
