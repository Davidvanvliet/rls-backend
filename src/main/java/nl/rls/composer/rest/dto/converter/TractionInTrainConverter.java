package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.rest.dto.mapper.TractionInTrainDtoMapper;

public class TractionInTrainConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return TractionInTrainDtoMapper.map((TractionInTrain)entity);
	}
}