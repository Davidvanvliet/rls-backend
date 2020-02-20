package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.Location;
import nl.rls.composer.rest.dto.mapper.LocationDtoMapper;
import org.dozer.CustomConverter;

public class LocationConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return LocationDtoMapper.map((Location) entity);
    }
}