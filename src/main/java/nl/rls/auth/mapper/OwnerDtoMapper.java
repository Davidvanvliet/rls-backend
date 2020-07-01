package nl.rls.auth.mapper;

import nl.rls.auth.domain.Owner;
import nl.rls.auth.domain.User;
import nl.rls.auth.rest.dto.OwnerDto;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class OwnerDtoMapper {
    public static OwnerDto map(Owner owner) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        OwnerDto ownerDto = mapper.map(owner, OwnerDto.class);
        List<String> userDtos = new ArrayList<>();
        for (User user : owner.getUsers()) {
            userDtos.add(user.getUserId());
        }
        ownerDto.setAuth0Ids(userDtos);
        return ownerDto;
    }

}