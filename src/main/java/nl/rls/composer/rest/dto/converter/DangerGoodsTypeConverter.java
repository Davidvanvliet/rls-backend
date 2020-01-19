package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.rest.dto.mapper.DangerGoodsTypeDtoMapper;

public class DangerGoodsTypeConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return DangerGoodsTypeDtoMapper.map((DangerGoodsType)entity);
	}
}