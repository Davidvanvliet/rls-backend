package nl.rls.composer.rest.dto.converter;

import nl.rls.composer.domain.Company;
import nl.rls.composer.rest.dto.mapper.CompanyDtoMapper;
import org.dozer.CustomConverter;

public class CompanyConverter implements CustomConverter {
    @Override
    public Object convert(Object dto, Object entity, Class<?> destinationClass,
                          Class<?> sourceClass) {
        return CompanyDtoMapper.map((Company) entity);
    }
}