package nl.rls.composer.rest.dto.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import nl.rls.composer.controller.CompanyController;
import nl.rls.composer.rest.dto.CompanyDto;

public class CompanyDtoMapper {
	public static CompanyDto map(nl.rls.composer.domain.Company company) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
			}
		};
		mapper.addMapping(mappingBuilder);
		CompanyDto companyDto = mapper.map(company, CompanyDto.class);
		companyDto.add(linkTo(methodOn(CompanyController.class).getById(company.getId())).withSelfRel());
		return companyDto;
	}


}
