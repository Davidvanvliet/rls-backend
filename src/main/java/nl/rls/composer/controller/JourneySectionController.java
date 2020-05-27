package nl.rls.composer.controller;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationRepository;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.*;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + JourneySectionController.PATH)
public class JourneySectionController {
    public static final String PATH = "/journeysections";
    private final LocationRepository locationRepository;
    private final JourneySectionRepository journeySectionRepository;
    private final SecurityContext securityContext;
    private final TrainRepository trainRepository;
    private final TrainCompositionService trainCompositionService;
    private final TrainActivityTypeRepository trainActivityTypeRepository;

    public JourneySectionController(LocationRepository locationRepository, JourneySectionRepository journeySectionRepository, SecurityContext securityContext, TrainRepository trainRepository, TrainCompositionService trainCompositionService, TrainActivityTypeRepository trainActivityTypeRepository) {
        this.locationRepository = locationRepository;
        this.journeySectionRepository = journeySectionRepository;
        this.securityContext = securityContext;
        this.trainRepository = trainRepository;
        this.trainCompositionService = trainCompositionService;
        this.trainActivityTypeRepository = trainActivityTypeRepository;
    }

    @GetMapping(value = "/{journeySectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<JourneySectionDto> getById(@PathVariable int journeySectionId) {
        int ownerId = securityContext.getOwnerId();
        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        JourneySectionDto trainCompositionJourneySectionDto = JourneySectionDtoMapper.map(journeySection);
        return ResponseBuilder.ok()
                .data(trainCompositionJourneySectionDto)
                .build();
    }

    @PutMapping(value = "/{journeySectionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<JourneySectionDto> update(@PathVariable int journeySectionId, @RequestBody @Valid JourneySectionPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        Integer locationId = DecodePath.decodeInteger(dto.getJourneySectionOriginUrl(), "locations");
        Optional<Location> location = locationRepository.findByLocationPrimaryCode(locationId);
        if (location.isPresent()) {
            journeySection.setJourneySectionOrigin(location.get());
        }

        locationId = DecodePath.decodeInteger(dto.getJourneySectionDestinationUrl(), "locations");
        location = locationRepository.findByLocationPrimaryCode(locationId);
        if (location.isPresent()) {
            journeySection.setJourneySectionDestination(location.get());
        }
        journeySection.getActivities().clear();
        for (ActivityInTrainAddDto activity : dto.getActivities()) {
            Integer activityId = DecodePath.decodeInteger(activity.getTrainActivityTypeUrl(), "trainactivitytypes");
            TrainActivityType trainActivityType = trainActivityTypeRepository.findById(activityId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train activity type with id %d", activityId)));
            journeySection.addActivity(trainActivityType);
        }
        journeySection.getTrainComposition().setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
        journeySectionRepository.save(journeySection);
        JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
        return ResponseBuilder.accepted()
                .data(journeySectionDto)
                .build();
    }


    @ApiOperation(value = "Copies or clones a complete TrainComposition including tractions and wagons.")
    @PutMapping(value = "/{journeySectionId}/clone")
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainDto> copyComposition(@PathVariable int journeySectionId,
                                              @RequestBody @Valid TrainCompositionCloneDto dto) {
        int ownerId = securityContext.getOwnerId();
        int fromSectionId = DecodePath.decodeInteger(dto.getJourneySectionUrl(), "journeysections");
        JourneySection fromJourneySection = journeySectionRepository.findByIdAndOwnerId(fromSectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        JourneySection toJourneySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        TrainComposition newTrainComposition = fromJourneySection.getTrainComposition().clone();
        newTrainComposition.setJourneySection(toJourneySection);
        toJourneySection.setTrainComposition(newTrainComposition);
        journeySectionRepository.save(toJourneySection);
        Train train = trainRepository.findByIdAndOwnerId(toJourneySection.getTrain().getId(), ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train with id %d", journeySectionId)));
        TrainDto resultDto = TrainDtoMapper.map(train);
        return ResponseBuilder.ok()
                .data(resultDto)
                .build();
    }

}
