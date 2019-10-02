package nl.rls.composer.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.TrainRunningData;

public class TrainRunningDataConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return TrainRunningDataDtoMapper.map((TrainRunningData)entity);
	}
}
