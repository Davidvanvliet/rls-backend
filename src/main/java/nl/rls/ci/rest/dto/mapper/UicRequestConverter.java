package nl.rls.ci.rest.dto.mapper;

import nl.rls.ci.domain.UicRequest;
import org.dozer.CustomConverter;

public class UicRequestConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return UicRequestDtoMapper.map((UicRequest) entity);
    }
}
