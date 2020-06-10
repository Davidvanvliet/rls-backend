package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.RollingStock;
import nl.rls.composer.rest.dto.mapper.RollingStockDtoMapper;
import org.dozer.CustomConverter;

public class RollingStockConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return RollingStockDtoMapper.map((RollingStock) entity);
    }
}
