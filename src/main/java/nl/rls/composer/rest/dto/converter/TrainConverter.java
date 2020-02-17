package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.Train;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import org.dozer.CustomConverter;


public class TrainConverter implements CustomConverter {
    @Override
    public Object convert(Object destination, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return TrainDtoMapper.map((Train) entity);
    }
}