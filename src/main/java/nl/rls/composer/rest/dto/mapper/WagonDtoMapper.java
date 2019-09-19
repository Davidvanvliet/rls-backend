package nl.rls.composer.rest.dto.mapper;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.WagonController;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.rest.dto.WagonDto;

public class WagonDtoMapper {

	public static WagonDto map(Wagon entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(Wagon.class, WagonDto.class)
				.fields("wagonNumberFreight", "wagonIdent",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.WagonIdentConverter"))
				;

			}
		};
		mapper.addMapping(mappingBuilder);
		WagonDto dto = mapper.map(entity, WagonDto.class);
		dto.add(linkTo(methodOn(WagonController.class).getById(entity.getId())).withSelfRel());
		return dto;
	}

}
