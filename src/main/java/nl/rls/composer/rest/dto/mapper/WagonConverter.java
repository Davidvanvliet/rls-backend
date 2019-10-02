package nl.rls.composer.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.Wagon;

public class WagonConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return WagonDtoMapper.map((Wagon)entity);
	}
}