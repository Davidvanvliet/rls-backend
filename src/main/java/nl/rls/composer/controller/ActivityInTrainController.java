package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.rest.dto.ActivityInTrainAddDto;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainActivityTypeDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + JourneySectionController.PATH)
public class ActivityInTrainController {
    private final SecurityContext securityContext;
    private final TrainActivityTypeRepository trainActivityTypeRepository;
    private final JourneySectionRepository journeySectionRepository;

    public ActivityInTrainController(SecurityContext securityContext, TrainActivityTypeRepository trainActivityTypeRepository, JourneySectionRepository journeySectionRepository) {
        this.securityContext = securityContext;
        this.trainActivityTypeRepository = trainActivityTypeRepository;
        this.journeySectionRepository = journeySectionRepository;
    }

    @GetMapping(value = "/{journeySectionId}/activities/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainActivityTypeDto> getActivityInTrain(@PathVariable int journeySectionId,
                                                             @PathVariable int activityId) {
        int ownerId = securityContext.getOwnerId();
        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        TrainActivityType trainActivityType = journeySection.getActivityById(activityId);
        TrainActivityTypeDto dto = TrainActivityTypeDtoMapper.map(trainActivityType);
        return ResponseBuilder.ok()
                .data(dto)
                .build();
    }

    @GetMapping(value = "/{journeySectionId}/activities", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TrainActivityTypeDto>> getAllActivityInTrain(@PathVariable int journeySectionId) {
        int ownerId = securityContext.getOwnerId();
        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        List<TrainActivityTypeDto> trainActivityTypeDtoList = new ArrayList<>();
        for (TrainActivityType trainActivityType : journeySection.getActivities()) {
            TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper.map(trainActivityType);
            trainActivityTypeDtoList.add(trainActivityTypeDto);
        }
        return ResponseBuilder.ok()
                .data(trainActivityTypeDtoList)
                .build();
    }

    @PostMapping(value = "/{journeySectionId}/activities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<JourneySectionDto> addActivity(@PathVariable int journeySectionId, @RequestBody @Valid ActivityInTrainAddDto dto) {
        int ownerId = securityContext.getOwnerId();
        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        int trainActivityTypeId = DecodePath.decodeInteger(dto.getTrainActivityTypeUrl(), "trainactivitytypes");
        TrainActivityType trainActivityType = trainActivityTypeRepository.findById(trainActivityTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find activity with id %d", trainActivityTypeId)));
        journeySection.addActivity(trainActivityType);
        journeySectionRepository.save(journeySection);
        JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
        return ResponseBuilder.created()
                .data(journeySectionDto)
                .build();
    }

    @PutMapping(value = "/{journeySectionId}/activities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<JourneySectionDto> addActivity(@PathVariable int journeySectionId,
                                                   @RequestBody @Valid List<ActivityInTrainAddDto> dtoList) {
        int ownerId = securityContext.getOwnerId();
        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        journeySection.getActivities().clear();
        for (ActivityInTrainAddDto dto : dtoList) {
            int trainActivityTypeId = DecodePath.decodeInteger(dto.getTrainActivityTypeUrl(), "trainactivitytypes");
            TrainActivityType trainActivityType = trainActivityTypeRepository.findById(trainActivityTypeId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find activity with id %d", trainActivityTypeId)));
            journeySection.addActivity(trainActivityType);
        }
        journeySectionRepository.save(journeySection);
        JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
        return ResponseBuilder.created()
                .data(journeySectionDto)
                .build();
    }

    @DeleteMapping(value = "/{journeySectionId}/activities/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<JourneySectionDto> removeActivity(@PathVariable int journeySectionId, @PathVariable int activityId) {
        int ownerId = securityContext.getOwnerId();

        JourneySection journeySection = journeySectionRepository.findByIdAndOwnerId(journeySectionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find journey section with id %d", journeySectionId)));
        journeySection.removeActivityById(activityId);
        journeySectionRepository.save(journeySection);
        JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
        return ResponseBuilder.ok()
                .data(journeySectionDto)
                .build();
    }

}
