package nl.rls.ci.soap.dto.mapper;

import nl.rls.ci.domain.MessageReference;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.domain.UicResponse;
import nl.rls.ci.soap.dto.LITechnicalAck;
import nl.rls.ci.soapinterface.UICMessage;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

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
                mapping(nl.rls.ci.soap.dto.MessageReference.class, MessageReference.class)
                        .fields("messageType", "messageType", FieldsMappingOptions.customConverter("nl.rls.ci.soap.dto.mapper.MessageTypeConverter"))
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        UicResponse uicResponse = mapper.map(dto, UicResponse.class);
        return uicResponse;
    }

}
