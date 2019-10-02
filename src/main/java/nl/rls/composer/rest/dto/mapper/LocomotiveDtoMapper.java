
package nl.rls.composer.rest.dto.mapper;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.LocomotiveController;
import nl.rls.composer.domain.Locomotive;
import nl.rls.composer.rest.dto.LocomotiveCreateDto;
import nl.rls.composer.rest.dto.LocomotiveDto;

public class LocomotiveDtoMapper {

	public static LocomotiveDto map(Locomotive entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
//				mapping(Locomotive.class, LocomotiveDto.class)
//				.fields("locomotiveNumberFreight", "locomotiveIdent",
//						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.LocomotiveIdentConverter"))
//				;

			}
		};
		mapper.addMapping(mappingBuilder);
		LocomotiveDto dto = mapper.map(entity, LocomotiveDto.class);
		dto.add(linkTo(methodOn(LocomotiveController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}
	
	public static Locomotive map(LocomotiveCreateDto dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		Locomotive entity = mapper.map(dto, Locomotive.class);
		return entity;
	}

}
