package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.WagonType;
import nl.rls.composer.rest.dto.mapper.WagonTypeDtoMapper;

public class WagonTypeConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return WagonTypeDtoMapper.map((WagonType)entity);
	}
}