package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.composer.controller.JourneySectionController;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.rest.dto.JourneySectionDto;

public class JourneySectionDtoMapper {

	public static JourneySectionDto map(JourneySection journeySection) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(JourneySection.class, JourneySectionDto.class)
						.fields("journeySectionOrigin", "journeySectionOrigin",
								FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.LocationIdentConverter"))
						.fields("journeySectionDestination", "journeySectionDestination", 
								FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.mapper.LocationIdentConverter"));
			}
		};
		mapper.addMapping(mappingBuilder);
		JourneySectionDto journeySectionDto = mapper.map(journeySection, JourneySectionDto.class);
		journeySectionDto.add(linkTo(methodOn(JourneySectionController.class).getJourneySection(journeySection.getId()))
				.withSelfRel());
		return journeySectionDto;
	}
}
