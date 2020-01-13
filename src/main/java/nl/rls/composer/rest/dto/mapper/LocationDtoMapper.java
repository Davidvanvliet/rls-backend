package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.CompanyController;
import nl.rls.composer.controller.LocationController;
import nl.rls.composer.domain.Location;
import nl.rls.composer.rest.dto.LocationDto;

public class LocationDtoMapper {

	public static LocationDto map(Location location) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		LocationDto locationIdentDto = mapper.map(location, LocationDto.class);
		locationIdentDto.add(linkTo(methodOn(LocationController.class).getById(location.getLocationPrimaryCode())).withSelfRel());
		locationIdentDto.add(linkTo(methodOn(CompanyController.class).getById(location.getResponsible().getId())).withRel("responsible").withTitle(location.getResponsible().getShortName()));
		return locationIdentDto;
	}

}
