package nl.rls.composer.rest.dto.mapper;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.WagonLoadController;
import nl.rls.composer.domain.WagonLoad;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.WagonLoadCreateDto;
import nl.rls.composer.rest.dto.WagonLoadDto;

public class WagonLoadDtoMapper {

	public static WagonLoadDto map(WagonLoad entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(WagonLoad.class, WagonLoadDto.class)
				.fields("wagon", "wagon",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.WagonConverter"))
				;

			}
		};
		mapper.addMapping(mappingBuilder);
		WagonLoadDto dto = mapper.map(entity, WagonLoadDto.class);
		dto.add(linkTo(methodOn(WagonLoadController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

	public static WagonLoad map(WagonLoadCreateDto dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		WagonLoad entity = mapper.map(dto, WagonLoad.class);
		return entity;
	}

}
