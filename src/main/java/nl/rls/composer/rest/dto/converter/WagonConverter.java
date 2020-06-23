package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.Wagon;
import nl.rls.composer.rest.dto.WagonDto;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;
import org.dozer.CustomConverter;

public class WagonConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if (sourceClass == WagonDto.class) {
            return WagonDtoMapper.map((WagonDto) entity);
        }
        return WagonDtoMapper.map((Wagon) entity);
    }
}