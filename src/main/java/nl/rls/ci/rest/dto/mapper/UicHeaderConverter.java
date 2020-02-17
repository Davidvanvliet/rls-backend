package nl.rls.ci.rest.dto.mapper;

import nl.rls.ci.domain.UicHeader;
import org.dozer.CustomConverter;

public class UicHeaderConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return UicHeaderDtoMapper.map((UicHeader) entity);
    }
}
