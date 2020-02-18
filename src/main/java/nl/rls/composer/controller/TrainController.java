package nl.rls.composer.controller;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.*;
import nl.rls.composer.repository.*;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainDto;
import nl.rls.composer.rest.dto.TrainPostDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "trains/")
public class TrainController {
    private final TrainRepository trainRepository;
    private final CompanyRepository companyRepository;
    private final ResponsibilityRepository responsibilityRepository;
    private final LocationRepository locationRepository;
    private final JourneySectionRepository journeySectionRepository;
    private final SecurityContext securityContext;

    public TrainController(TrainRepository trainRepository, CompanyRepository companyRepository, ResponsibilityRepository responsibilityRepository, LocationRepository locationRepository, JourneySectionRepository journeySectionRepository, SecurityContext securityContext) {
        this.trainRepository = trainRepository;
        this.companyRepository = companyRepository;
        this.responsibilityRepository = responsibilityRepository;
        this.locationRepository = locationRepository;
        this.journeySectionRepository = journeySectionRepository;
        this.securityContext = securityContext;
    }

    @ApiOperation(value = "Gets a complete Train object")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        System.out.println("TrainController " + ownerId);
        Iterable<Train> trainList = trainRepository.findByOwnerId(ownerId);
        System.out.println("TrainController " + ownerId);
        List<TrainDto> trainDtoList = new ArrayList<>();

        for (Train train : trainList) {
            trainDtoList.add(TrainDtoMapper.map(train));
        }
//		Link trainsLink = linkTo(methodOn(TrainController.class).getAll()).withSelfRel();
//		Resources<TrainDto> trains = new Resources<TrainDto>(trainDtoList, trainsLink);
        return ResponseEntity.ok(trainDtoList);
    }

    //
    @GetMapping(value = "{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainDto> getById(@PathVariable int id) {
        int ownerId = securityContext.getOwnerId();
        Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainDto trainDto = TrainDtoMapper.map(optional.get());
            return ResponseEntity.ok(trainDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainDto> create(@RequestBody TrainPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Train train = new Train();
        train.setOwnerId(ownerId);
        train.setOperationalTrainNumber(dto.getOperationalTrainNumber());
        train.setScheduledDateTimeAtTransfer(dto.getScheduledDateTimeAtTransfer());
        train.setScheduledTimeAtHandover(dto.getScheduledTimeAtHandover());
        train.setTrainType(dto.getTrainType());

        /* ProRail = 0084 */
        List<Company> recipient = companyRepository.findByCode("0084");
        if (recipient.size() == 1) {
            train.setTransfereeIM(recipient.get(0));
        }

        Integer locationId = DecodePath.decodeInteger(dto.getTransferPoint(), "locations");
        Optional<Location> locationIdent = locationRepository.findById(locationId);
        if (locationIdent.isPresent()) {
            train.setTransferPoint(locationIdent.get());
        }

        train = trainRepository.save(train);
        if (train != null) {
            TrainDto resultDto = TrainDtoMapper.map(train);
            return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
                    .body(resultDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainDto> update(@PathVariable Integer id, @RequestBody TrainPostDto trainCreateDto) {
        int ownerId = securityContext.getOwnerId();
        Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            Train train = TrainDtoMapper.map(trainCreateDto);
            train.setOwnerId(ownerId);
            train = trainRepository.save(train);
            if (train != null) {
                TrainDto dto = TrainDtoMapper.map(train);

                return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
                        .body(dto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{id}/journeysections/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JourneySectionDto>> getAllJourneySections(@PathVariable int id) {
        int ownerId = securityContext.getOwnerId();
        Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {

            Iterable<JourneySection> sectionList = journeySectionRepository
                    .findByTrainAndOwnerId(optional.get(), ownerId);
            System.out.println("TrainCompositionJourneySectionController " + ownerId);
            List<JourneySectionDto> journeySectionDtoList = new ArrayList<>();

            for (JourneySection entity : sectionList) {
                journeySectionDtoList.add(JourneySectionDtoMapper.map(entity));
            }
//			Link trainsLink = linkTo(methodOn(TrainController.class).getAllJourneySections(id)).withSelfRel();
//			Resources<TrainCompositionJourneySectionDto> trains = new Resources<TrainCompositionJourneySectionDto>(
//					journeySectionDtoList, trainsLink);
            return ResponseEntity.ok(journeySectionDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "{id}/journeysections/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainDto> postJourneySection(@PathVariable Integer id,
                                                       @RequestBody JourneySectionPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
        if (!optional.isPresent()) {
            ResponseEntity.notFound();
        }
        Train train = optional.get();
        JourneySection journeySection = new JourneySection(ownerId);
        Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOriginUrl(), "locations");
        Optional<Location> optionalLocation = locationRepository.findByLocationPrimaryCode(locationIdentId);
        if (optionalLocation.isPresent()) {
            journeySection.setJourneySectionOrigin(optionalLocation.get());
        }

        locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestinationUrl(), "locations");
        optionalLocation = locationRepository.findByLocationPrimaryCode(locationIdentId);
        if (optionalLocation.isPresent()) {
            journeySection.setJourneySectionDestination(optionalLocation.get());
        }
        Responsibility responsibility = responsibilityRepository.findByOwnerId(ownerId);
        journeySection.setResponsibilityActualSection(responsibility);
        journeySection.setResponsibilityNextSection(responsibility);

        journeySection.setTrainComposition(new TrainComposition(ownerId));
        journeySection.getTrainComposition().setJourneySection(journeySection);
        journeySection.getTrainComposition().setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
        journeySection.getTrainComposition().setBrakeType(dto.getBrakeType());
        journeySection.getTrainComposition().setBrakeWeight(dto.getBrakeWeight());
        journeySection.getTrainComposition().setTrainMaxSpeed(dto.getTrainMaxSpeed());
        journeySection.getTrainComposition().setMaxAxleWeight(dto.getMaxAxleWeight());
        journeySection = journeySectionRepository.save(journeySection);
        train.addJourneySection(journeySection);
        train = trainRepository.save(train);

        if (train != null) {
            TrainDto resultDto = TrainDtoMapper.map(train);

            return ResponseEntity.created(linkTo(methodOn(JourneySectionController.class)
                    .getById(journeySection.getId())).toUri()).body(resultDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "{id}/journeysections/{sectionId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainDto> removeJourneysection(@PathVariable int id, @PathVariable int sectionId) {
        int ownerId = securityContext.getOwnerId();
        Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Train train = optional.get();
        JourneySection journeySection = train.getJourneySectionById(sectionId);
        train.removeJourneySection(journeySection);
        train = trainRepository.save(train);
        if (train != null) {
            TrainDto resultDto = TrainDtoMapper.map(train);
            return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
                    .body(resultDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
