package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.Traction;
import nl.rls.composer.rest.dto.TractionDto;
import nl.rls.composer.rest.dto.mapper.TractionDtoMapper;
import org.dozer.CustomConverter;

public class TractionConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        if (entity instanceof TractionDto) {
            return TractionDtoMapper.map((TractionDto) entity);
        }
        return TractionDtoMapper.map((Traction) entity);
    }
}