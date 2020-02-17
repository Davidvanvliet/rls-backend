package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import org.dozer.CustomConverter;


public class TrainCompositionConverter implements CustomConverter {
    @Override
    public Object convert(Object destination, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return TrainCompositionDtoMapper.map((TrainComposition) entity);
    }
}