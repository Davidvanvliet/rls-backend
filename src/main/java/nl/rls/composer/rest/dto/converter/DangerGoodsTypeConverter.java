package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.rest.dto.mapper.DangerGoodsTypeDtoMapper;
import org.dozer.CustomConverter;

public class DangerGoodsTypeConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return DangerGoodsTypeDtoMapper.map((DangerGoodsType) entity);
    }
}