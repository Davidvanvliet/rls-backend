package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.Traction;
import nl.rls.composer.rest.dto.mapper.TractionDtoMapper;
import org.dozer.CustomConverter;

public class TractionConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return TractionDtoMapper.map((Traction) entity);
    }
}