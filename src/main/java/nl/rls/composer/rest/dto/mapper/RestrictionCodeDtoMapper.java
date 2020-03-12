package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.RestrictionCodeController;
import nl.rls.composer.rest.dto.RestrictionCodeDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RestrictionCodeDtoMapper {
    public static RestrictionCodeDto map(nl.rls.composer.domain.code.RestrictionCode restrictionCode) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        RestrictionCodeDto restrictionCodeDto = mapper.map(restrictionCode, RestrictionCodeDto.class);
        restrictionCodeDto.add(linkTo(methodOn(RestrictionCodeController.class).getRestrictionCode(restrictionCode.getId())).withSelfRel());
        return restrictionCodeDto;
    }


}
