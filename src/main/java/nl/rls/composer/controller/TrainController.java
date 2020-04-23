package nl.rls.composer.controller;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.*;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.*;
import nl.rls.composer.rest.dto.*;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/trains")
public class TrainController {
    private final TrainRepository trainRepository;
    private final CompanyRepository companyRepository;
    private final ResponsibilityRepository responsibilityRepository;
    private final LocationRepository locationRepository;
    private final JourneySectionRepository journeySectionRepository;
    private final SecurityContext securityContext;
    private final TrainActivityTypeRepository trainActivityTypeRepository;

    public TrainController(TrainRepository trainRepository, CompanyRepository companyRepository, ResponsibilityRepository responsibilityRepository, LocationRepository locationRepository, JourneySectionRepository journeySectionRepository, SecurityContext securityContext, TrainActivityTypeRepository trainActivityTypeRepository) {
        this.trainRepository = trainRepository;
        this.companyRepository = companyRepository;
        this.responsibilityRepository = responsibilityRepository;
        this.locationRepository = locationRepository;
        this.journeySectionRepository = journeySectionRepository;
        this.securityContext = securityContext;
        this.trainActivityTypeRepository = trainActivityTypeRepository;
    }

    @ApiOperation(value = "Gets a complete Train object")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TrainDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        List<Train> trainList = trainRepository.findByOwnerId(ownerId);
        List<TrainDto> trainDtoList = trainList.stream()
                .map(TrainDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(trainDtoList)
                .build();
    }

    @GetMapping(value = "/{trainId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainDto> getById(@PathVariable int trainId) {
        int ownerId = securityContext.getOwnerId();
        Train train = trainRepository.findByIdAndOwnerId(trainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train with id %d", trainId)));
        TrainDto trainDto = TrainDtoMapper.map(train);
        return ResponseBuilder.ok()
                .data(trainDto)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainDto> create(@RequestBody @Valid TrainPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Train train = new Train();
        train.setOwnerId(ownerId);
        train.setOperationalTrainNumber(dto.getOperationalTrainNumber());
        train.setScheduledDateTimeAtTransfer(dto.getScheduledDateTimeAtTransfer());
        train.setScheduledTimeAtHandover(dto.getScheduledTimeAtHandover());
        train.setTrainType(dto.getTrainType());

        /* ProRail = 0084 */
        Optional<Company> recipient = companyRepository.findByCode("0084");
        if (recipient.isPresent()) {
            train.setTransfereeIM(recipient.get());
        }

        Integer locationId = DecodePath.decodeInteger(dto.getTransferPoint(), "locations");
        Optional<Location> locationIdent = locationRepository.findById(locationId);
        if (locationIdent.isPresent()) {
            train.setTransferPoint(locationIdent.get());
        }

        train = trainRepository.save(train);
        TrainDto trainDto = TrainDtoMapper.map(train);
        return ResponseBuilder.created()
                .data(trainDto)
                .build();
    }

    @PutMapping(value = "/{trainId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainDto> update(@PathVariable int trainId, @RequestBody @Valid TrainPostDto trainCreateDto) {
        int ownerId = securityContext.getOwnerId();
        if (!trainRepository.existsByIdAndOwnerId(trainId, ownerId)) {
            throw new EntityNotFoundException(String.format("Could not find train with id %d", trainId));
        }
        Train train = TrainDtoMapper.map(trainCreateDto);
        train.setOwnerId(ownerId);
        train.setId(trainId);
        train = trainRepository.save(train);
        TrainDto dto = TrainDtoMapper.map(train);
        return ResponseBuilder.created()
                .data(dto)
                .build();
    }

    @GetMapping(value = "/{trainId}/journeysections", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<JourneySectionDto>> getAllJourneySections(@PathVariable int trainId) {
        int ownerId = securityContext.getOwnerId();
        if (!trainRepository.existsByIdAndOwnerId(trainId, ownerId)) {
            throw new EntityNotFoundException(String.format("Could not find train with id %d", trainId));
        }
        List<JourneySection> sectionList = journeySectionRepository.findByTrainIdAndOwnerId(trainId, ownerId);
        List<JourneySectionDto> journeySectionDtoList = sectionList.stream()
                .map(JourneySectionDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(journeySectionDtoList)
                .build();
    }

    @PostMapping(value = "/{trainId}/journeysections", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainDto> postJourneySection(@PathVariable int trainId,
                                                 @RequestBody @Valid JourneySectionPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Train train = trainRepository.findByIdAndOwnerId(trainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train with id %d", trainId)));
        JourneySection journeySection = new JourneySection(ownerId);
        Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOriginUrl(), "locations");
        Optional<Location> optionalLocation = locationRepository.findByLocationPrimaryCode(locationIdentId);
        Location origin = null;
        if (optionalLocation.isPresent()) {
        	origin = optionalLocation.get();
            journeySection.setJourneySectionOrigin(origin);
        }

        locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestinationUrl(), "locations");
        optionalLocation = locationRepository.findByLocationPrimaryCode(locationIdentId);
        Location destination = null;
        if (optionalLocation.isPresent()) {
        	destination = optionalLocation.get();
            journeySection.setJourneySectionDestination(destination);
        }

        Responsibility responsibility = null;
        Optional<Responsibility> optionalResponsibility = responsibilityRepository.findByOwnerId(ownerId);
        if (!optionalResponsibility.isPresent()) {
            Company responsibleRU = null;
            Optional<Company> optional = companyRepository.findByCode(securityContext.getCompanyCode());
            if (optional.isPresent()) {
            	responsibleRU = optional.get();
                responsibility = new Responsibility(ownerId);
                responsibility.setResponsibleIM(origin.getResponsible());
                responsibility.setResponsibleRU(responsibleRU);
                responsibilityRepository.save(responsibility);    	
            }
        } else {
        	responsibility = optionalResponsibility.get();
        }
        journeySection.setResponsibilityActualSection(responsibility);
        journeySection.setResponsibilityNextSection(responsibility);
        
        for (ActivityInTrainAddDto activity : dto.getActivities()) {
            Integer activityId = DecodePath.decodeInteger(activity.getTrainActivityTypeUrl(), "trainactivitytypes");
            TrainActivityType trainActivityType = trainActivityTypeRepository.findById(activityId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train activity type with id %d", activityId)));
            journeySection.addActivity(trainActivityType);
        }

        journeySection.setTrainComposition(new TrainComposition(ownerId));
        journeySection.getTrainComposition().setJourneySection(journeySection);
        journeySection.getTrainComposition().setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
        journeySection.getTrainComposition().setBrakeType(dto.getBrakeType());
        journeySection = journeySectionRepository.save(journeySection);
        train.addJourneySection(journeySection);
        train = trainRepository.save(train);

        TrainDto resultDto = TrainDtoMapper.map(train);
        return ResponseBuilder.created()
                .data(resultDto)
                .build();
    }

    @DeleteMapping(value = "/{trainId}/journeysections/{journeySectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainDto> removeJourneySection(@PathVariable int trainId, @PathVariable int journeySectionId) {
        int ownerId = securityContext.getOwnerId();
        Train train = trainRepository.findByIdAndOwnerId(trainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train with id %d", trainId)));
        JourneySection journeySection = train.getJourneySectionById(journeySectionId);
        train.removeJourneySection(journeySection);
        train = trainRepository.save(train);
        TrainDto resultDto = TrainDtoMapper.map(train);
        return ResponseBuilder.ok()
                .data(resultDto)
                .build();

    }


}
