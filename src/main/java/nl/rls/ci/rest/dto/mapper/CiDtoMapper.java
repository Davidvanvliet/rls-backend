package nl.rls.ci.rest.dto.mapper;

import nl.rls.ci.controller.CiController;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.rest.dto.CiDto;
import nl.rls.ci.rest.dto.CiPostDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CiDtoMapper {

    public static CiDto map(CiMessage entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(CiMessage.class, CiDto.class)
                        .fields("uicRequest", "uicRequest",
                                FieldsMappingOptions.customConverter("nl.rls.ci.rest.dto.mapper.UicRequestConverter"))
                        .fields("uicHeader", "uicHeader",
                                FieldsMappingOptions.customConverter("nl.rls.ci.rest.dto.mapper.UicHeaderConverter"))
                ;
            }
        };
        mapper.addMapping(mappingBuilder);
        CiDto ciDto = mapper.map(entity, CiDto.class);
        ciDto.add(linkTo(methodOn(CiController.class).getMessage(entity.getId())).withSelfRel());
        return ciDto;
    }

    public static CiMessage map(CiPostDto ciPostDto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        CiMessage ciMessage = mapper.map(ciPostDto, CiMessage.class);
        return ciMessage;
    }

}
