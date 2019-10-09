package nl.rls.ci.soap.dto.mapper;

import org.dozer.DozerBeanMapper;

import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.domain.UicResponse;
import nl.rls.ci.soap.dto.UICMessage;
import nl.rls.ci.soap.dto.UICMessageResponse;

public class CiDtoMapper {

	public static UICMessage map(UicRequest entity) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		UICMessage uicMessage = mapper.map(entity, UICMessage.class);
		return uicMessage;
	}

	public static UicResponse map(UICMessageResponse dto) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		UicResponse uicResponse = mapper.map(dto, UicResponse.class);
		return uicResponse;
	}

}
