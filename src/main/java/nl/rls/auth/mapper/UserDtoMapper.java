package nl.rls.auth.mapper;

import nl.rls.auth.domain.User;
import nl.rls.auth.rest.dto.UserDto;
import org.dozer.DozerBeanMapper;

public class UserDtoMapper {
    public static UserDto map(User user) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        return mapper.map(user, UserDto.class);
    }
}
