package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.DangerGoodsInWagonController;
import nl.rls.composer.domain.DangerGoodsInWagon;
import nl.rls.composer.rest.dto.DangerGoodsInWagonDto;

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
