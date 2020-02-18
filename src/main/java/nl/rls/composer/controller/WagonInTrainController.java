package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.WagonInTrainAddDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;
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
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class WagonInTrainController {
    public static final String PATH = "wagonintrains/";
    private final SecurityContext securityContext;
    private final WagonRepository wagonRepository;
    private final TrainCompositionService trainCompositionService;
    private final TrainCompositionRepository trainCompositionRepository;

    public WagonInTrainController(SecurityContext securityContext, WagonRepository wagonRepository, TrainCompositionService trainCompositionService, TrainCompositionRepository trainCompositionRepository) {
        this.securityContext = securityContext;
        this.wagonRepository = wagonRepository;
        this.trainCompositionService = trainCompositionService;
        this.trainCompositionRepository = trainCompositionRepository;
    }

    @GetMapping(value = "{trainCompositionId}/wagons/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WagonInTrainDto>> getAllWagonInTrain(@PathVariable Integer trainCompositionId) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(trainCompositionId, ownerId);
        if (optional.isPresent()) {
            TrainComposition entity = optional.get();
            List<WagonInTrainDto> wagonInTrainDtoList = new ArrayList<WagonInTrainDto>();
            for (WagonInTrain wagonInTrain : entity.getWagons()) {
                WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
                wagonInTrainDtoList.add(wagonInTrainDto);
            }
            return ResponseEntity.ok(wagonInTrainDtoList);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "{trainCompositionId}/wagons/{wagonId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WagonInTrainDto> getWagonInTrain(@PathVariable Integer trainCompositionId, @PathVariable Integer wagonId) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(trainCompositionId, ownerId);
        if (optional.isPresent()) {
            TrainComposition entity = optional.get();
            WagonInTrain wagonInTrain = entity.getWagonById(wagonId);
            WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
            return ResponseEntity.ok(wagonInTrainDto);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping(value = "{trainCompositionId}/wagons/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> addWagon(@PathVariable int trainCompositionId,
                                                        @RequestBody WagonInTrainAddDto dto) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(trainCompositionId, ownerId);
        if (optional.isPresent()) {
            int wagonId = DecodePath.decodeInteger(dto.getWagonUrl(), "wagons");
            Optional<Wagon> wagon = wagonRepository.findByOwnerIdAndId(ownerId, wagonId);
            if (wagon.isPresent()) {
                TrainComposition trainComposition = optional.get();

                WagonInTrain wagonInTrain = new WagonInTrain();
                wagonInTrain.setOwnerId(ownerId);
                wagonInTrain.setWagon(wagon.get());
                wagonInTrain.setPosition(dto.getPosition());

                wagonInTrain.setBrakeType(BrakeType.G);
                wagonInTrain.setBrakeWeight(dto.getBrakeWeight());
                wagonInTrain.setTotalLoadWeight(dto.getTotalLoadWeight());
                wagonInTrain.setWagonMaxSpeed(dto.getWagonMaxSpeed());
                trainCompositionService.addWagonToTrain(trainComposition, wagonInTrain);
                TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                        .map(trainComposition);
                return ResponseEntity
                        .created(linkTo(methodOn(TrainCompositionController.class)
                                .getById(trainComposition.getId())).toUri())
                        .body(trainCompositionDto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "{trainCompositionId}/wagons/{wagonInTrainId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> moveWagon(@PathVariable int trainCompositionId,
                                                         @PathVariable int wagonInTrainId, @RequestParam("position") int position) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(trainCompositionId, ownerId);
        if (optional.isPresent()) {
            TrainComposition trainComposition = optional.get();
            trainCompositionService.moveWagonById(trainComposition, wagonInTrainId, position);
            TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                    .map(trainComposition);
            return ResponseEntity.accepted().body(trainCompositionDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "{id}/wagons/{wagonInTrainId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> removeWagon(@PathVariable int id,
                                                           @PathVariable int wagonInTrainId) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainComposition trainComposition = optional.get();
            trainComposition.removeWagonById(wagonInTrainId);
//			wagonInTrainRepository.saveAll(trainCompositionJourneySection.getWagons());
            trainCompositionRepository.save(trainComposition);
            TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                    .map(trainComposition);
            return ResponseEntity.ok(trainCompositionDto);
        }
        return ResponseEntity.notFound().build();
    }

}
