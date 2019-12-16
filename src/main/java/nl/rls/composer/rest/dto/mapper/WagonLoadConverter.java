package nl.rls.composer.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.WagonLoad;

public class WagonLoadConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return WagonLoadDtoMapper.map((WagonLoad)entity);
	}
}