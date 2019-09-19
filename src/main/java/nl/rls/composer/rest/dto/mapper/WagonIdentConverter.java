package nl.rls.composer.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.WagonIdent;

public class WagonIdentConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return WagonIdentDtoMapper.map((WagonIdent)entity);
	}
}