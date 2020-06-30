package nl.rls.auth.mapper;

import nl.rls.auth.domain.Owner;
import nl.rls.auth.rest.dto.OwnerDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

public class OwnerDtoMapper {
    public static OwnerDto map(Owner owner) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        return mapper.map(owner, OwnerDto.class);
    }

}