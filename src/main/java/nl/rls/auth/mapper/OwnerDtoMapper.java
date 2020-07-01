package nl.rls.auth.mapper;

import nl.rls.auth.domain.Owner;
import nl.rls.auth.domain.User;
import nl.rls.auth.rest.dto.OwnerDto;
import nl.rls.auth.rest.dto.UserDto;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import java.util.ArrayList;
import java.util.List;

public class OwnerDtoMapper {
    public static OwnerDto map(Owner owner) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        OwnerDto ownerDto = mapper.map(owner, OwnerDto.class);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : owner.getUsers()) {
            userDtos.add(UserDtoMapper.map(user));
        }
        ownerDto.setUsers(userDtos);
        return ownerDto;
    }

}