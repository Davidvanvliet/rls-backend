package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.WagonIdentController;
import nl.rls.composer.domain.WagonIdent;
import nl.rls.composer.rest.dto.WagonIdentDto;

public class WagonIdentDtoMapper {

	public static WagonIdentDto map(WagonIdent wagonIdent) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(WagonIdent.class, WagonIdentDto.class).fields("wagonTechData", "wagonTechData",
						FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.WagonTechDataConverter"));
			}
		};
		mapper.addMapping(mappingBuilder);
		WagonIdentDto wagonIdentDto = mapper.map(wagonIdent, WagonIdentDto.class);
		wagonIdentDto.add(linkTo(methodOn(WagonIdentController.class).getById(wagonIdent.getId())).withSelfRel());
		return wagonIdentDto;
	}

}
