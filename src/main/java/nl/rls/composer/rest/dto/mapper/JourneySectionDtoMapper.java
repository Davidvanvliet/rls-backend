package nl.rls.composer.rest.dto.mapper;

import nl.rls.composer.controller.JourneySectionController;
import nl.rls.composer.controller.LocationController;
import nl.rls.composer.controller.TrainCompositionController;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class JourneySectionDtoMapper {

    public static JourneySectionDto map(JourneySection entity) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(JourneySection.class, JourneySectionDto.class)
                        .fields("trainComposition", "trainComposition",
                                FieldsMappingOptions.customConverter("nl.rls.composer.rest.dto.converter.TrainCompositionConverter"));
            }
        };
        mapper.addMapping(mappingBuilder);
        JourneySectionDto dto = mapper.map(entity, JourneySectionDto.class);

        List<TrainActivityTypeDto> trainActivityTypeDtoList = new ArrayList<TrainActivityTypeDto>();
        for (TrainActivityType trainActivityType : entity.getActivities()) {
            TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper.map(trainActivityType);
            trainActivityTypeDtoList.add(trainActivityTypeDto);
        }
        dto.setActivities(trainActivityTypeDtoList);

//        dto.add(linkTo(methodOn(CompanyController.class)
//                .getById(entity.getResponsibilityActualSection().getResponsibleRU().getId()))
//                .withRel("responsibilityActualSectionRU")
//                .withTitle(entity.getResponsibilityActualSection().getResponsibleRU().getName()));
//        dto.add(linkTo(methodOn(CompanyController.class)
//                .getById(entity.getResponsibilityActualSection().getResponsibleIM().getId()))
//                .withRel("responsibilityActualSectionIM")
//                .withTitle(entity.getResponsibilityActualSection().getResponsibleIM().getName()));
//        dto.add(linkTo(methodOn(CompanyController.class)
//                .getById(entity.getResponsibilityNextSection().getResponsibleRU().getId()))
//                .withRel("responsibilityNextSectionRU")
//                .withTitle(entity.getResponsibilityNextSection().getResponsibleRU().getName()));
//        dto.add(linkTo(methodOn(CompanyController.class)
//                .getById(entity.getResponsibilityNextSection().getResponsibleIM().getId()))
//                .withRel("responsibilityNextSectionIM")
//                .withTitle(entity.getResponsibilityNextSection().getResponsibleIM().getName()));
        dto.add(linkTo(methodOn(LocationController.class)
                .getById(entity.getJourneySectionOrigin().getLocationPrimaryCode())).withRel("journeySectionOrigin")
                .withTitle(entity.getJourneySectionOrigin().getPrimaryLocationName()));
        dto.add(linkTo(methodOn(LocationController.class)
                .getById(entity.getJourneySectionDestination().getLocationPrimaryCode()))
                .withRel("journeySectionDestination")
                .withTitle(entity.getJourneySectionDestination().getPrimaryLocationName()));
        if (entity.getTrainComposition() != null) {
            dto.add(linkTo(methodOn(TrainCompositionController.class).getById(entity.getTrainComposition().getId()))
                    .withRel("trainComposition"));
        }
        dto.add(linkTo(methodOn(JourneySectionController.class).getById(entity.getId())).withSelfRel().withTitle(entity.getJourneySectionDestination().getPrimaryLocationName()));
        return dto;
    }

    public static JourneySection map(JourneySectionPostDto dto) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
            }
        };
        mapper.addMapping(mappingBuilder);
        JourneySection entity = mapper.map(dto, JourneySection.class);
        return entity;
    }

}
