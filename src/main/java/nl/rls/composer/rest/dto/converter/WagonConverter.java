package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.Wagon;
import nl.rls.composer.rest.dto.mapper.WagonDtoMapper;

public class WagonConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return WagonDtoMapper.map((Wagon)entity);
	}
}