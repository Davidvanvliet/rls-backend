package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import org.dozer.CustomConverter;

public class JourneySectionConverter implements CustomConverter {
    @Override
    public Object convert(Object destination, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return JourneySectionDtoMapper.map((JourneySection) entity);
    }
}