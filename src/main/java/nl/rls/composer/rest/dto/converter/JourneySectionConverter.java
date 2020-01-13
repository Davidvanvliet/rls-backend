package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;

public class JourneySectionConverter implements CustomConverter {
	@Override
	public Object convert(Object destination, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return JourneySectionDtoMapper.map((JourneySection)entity);
	}
}