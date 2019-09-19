package nl.rls.composer.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.TrainCompositionJourneySection;

public class TrainCompositionJourneySectionConverter implements CustomConverter {
	@Override
	public Object convert(Object destination, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return TrainCompositionJourneySectionDtoMapper.map((TrainCompositionJourneySection)entity);
	}
}