package nl.rls.ci.soap.dto.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.domain.UicResponse;
import nl.rls.ci.soap.dto.LITechnicalAck;
import nl.rls.ci.soap.dto.UICMessage;
import nl.rls.ci.soap.dto.UICMessageResponse;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.rest.dto.TractionInTrainDto;

public class CiDtoMapper {

	public static UICMessage map(UicRequest entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		UICMessage uicMessage = mapper.map(entity, UICMessage.class);
		return uicMessage;
	}

	public static UicResponse map(LITechnicalAck dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(LITechnicalAck.class, UicResponse.class)
				.fields("responseStatus", "responseStatus")
				;
			}
		};
		mapper.addMapping(mappingBuilder);		
		UicResponse uicResponse = mapper.map(dto, UicResponse.class);
		return uicResponse;
	}

}
