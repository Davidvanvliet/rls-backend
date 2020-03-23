package nl.rls.ci.soap.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

public class TrainCompositionMessageConverter implements CustomConverter {

	@Override
	public Object convert(Object destination, Object entity, Class<?> destinationClass, Class<?> sourceClass) {
		return TrainCompositionMessageXmlMapper.map((TrainCompositionMessage) entity);
	}

}
