package nl.rls.composer.rest.dto.mapper;

import org.dozer.CustomConverter;

import nl.rls.composer.domain.Company;

public class CompanyConverter implements CustomConverter {
	@Override
	public Object convert(Object dto, Object entity, Class<?> destinationClass,
			Class<?> sourceClass) {
		return CompanyDtoMapper.map((Company)entity);
	}
}