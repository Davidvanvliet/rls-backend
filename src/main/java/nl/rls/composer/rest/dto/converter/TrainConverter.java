package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.Train;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;


public class TrainConverter implements CustomConverter {
	@Override
	public Object convert(Object destination, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return TrainDtoMapper.map((Train)entity);
	}
}