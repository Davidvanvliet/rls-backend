package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.Traction;
import nl.rls.composer.rest.dto.mapper.TractionDtoMapper;

public class TractionConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return TractionDtoMapper.map((Traction)entity);
	}
}