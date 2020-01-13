package nl.rls.composer.rest.dto.converter;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;


public class TrainCompositionConverter implements CustomConverter {
	@Override
	public Object convert(Object destination, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return TrainCompositionDtoMapper.map((TrainComposition)entity);
	}
}