package nl.rls.ci.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.ci.domain.UicHeader;

public class UicHeaderConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return UicHeaderDtoMapper.map((UicHeader)entity);
	}
}
