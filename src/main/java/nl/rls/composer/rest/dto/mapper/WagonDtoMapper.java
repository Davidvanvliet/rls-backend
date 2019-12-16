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

	public static WagonDto map(Wagon wagon) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(Wagon.class, WagonDto.class).fields("wagonTechData", "wagonTechData",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.WagonTechDataConverter"));
			}
		};
		mapper.addMapping(mappingBuilder);
		WagonDto wagonDto = mapper.map(wagon, WagonDto.class);
		wagonDto.add(linkTo(methodOn(WagonController.class).getById(wagon.getId())).withSelfRel());
		return wagonDto;
	}

}
