package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.CompanyController;
import nl.rls.composer.controller.LocationIdentController;
import nl.rls.composer.domain.LocationIdent;
import nl.rls.composer.rest.dto.LocationIdentDto;

public class LocationIdentDtoMapper {

	public static LocationIdentDto map(LocationIdent locationIdent) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		LocationIdentDto locationIdentDto = mapper.map(locationIdent, LocationIdentDto.class);
		locationIdentDto.add(linkTo(methodOn(LocationIdentController.class).getById(locationIdent.getLocationPrimaryCode())).withSelfRel());
		locationIdentDto.add(linkTo(methodOn(CompanyController.class).getById(locationIdent.getResponsible().getId())).withRel("responsible").withTitle(locationIdent.getResponsible().getShortName()));
		return locationIdentDto;
	}

}
