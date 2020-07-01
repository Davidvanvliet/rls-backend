package nl.rls.auth.mapper.converter;

import nl.rls.auth.domain.User;
import nl.rls.auth.mapper.UserDtoMapper;
import org.dozer.CustomConverter;

public class UserConverter implements CustomConverter {
    @Override
    public Object convert(Object o, Object o1, Class<?> aClass, Class<?> aClass1) {
        return UserDtoMapper.map((User) o1);
    }
}
