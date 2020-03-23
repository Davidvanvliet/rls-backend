package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.rest.dto.TractionInTrainDto;
import nl.rls.composer.rest.dto.TractionInTrainPostDto;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.mapper.TractionInTrainDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import nl.rls.composer.service.TrainCompositionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class TractionInTrainController {
    private final SecurityContext securityContext;
    private final TractionRepository tractionRepository;
    private final TractionInTrainRepository tractionInTrainRepository;
    private final TrainCompositionService trainCompositionService;
    private final TrainCompositionRepository trainCompositionRepository;

    public TractionInTrainController(SecurityContext securityContext, TractionRepository tractionRepository, TractionInTrainRepository tractionInTrainRepository, TrainCompositionService trainCompositionService, TrainCompositionRepository trainCompositionRepository) {
        this.securityContext = securityContext;
        this.tractionRepository = tractionRepository;
        this.tractionInTrainRepository = tractionInTrainRepository;
        this.trainCompositionService = trainCompositionService;
        this.trainCompositionRepository = trainCompositionRepository;
    }

    @GetMapping(value = "/{id}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TractionInTrainDto> getTractionInTrain(@PathVariable Integer id, @PathVariable Integer tractionId) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainComposition entity = optional.get();
            TractionInTrain tractionInTrain = entity.getTractionById(tractionId);
            TractionInTrainDto tractionInTrainDto = TractionInTrainDtoMapper.map(tractionInTrain);
            return ResponseEntity.ok(tractionInTrainDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}/tractions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TractionInTrainDto>> getAllTractionInTrain(@PathVariable Integer id) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainComposition entity = optional.get();
            List<TractionInTrainDto> tractionInTrainDtoList = new ArrayList<TractionInTrainDto>();
            for (TractionInTrain tractionInTrain : entity.getTractions()) {
                TractionInTrainDto tractionInTrainDto = TractionInTrainDtoMapper.map(tractionInTrain);
                tractionInTrainDtoList.add(tractionInTrainDto);
            }
//			Link link = linkTo(methodOn(TractionInTrainController.class).getAllTractionInTrain(id))
//					.withSelfRel();
//			CollectionModel<TractionInTrainDto> tractionInTrainDtos = new CollectionModel<TractionInTrainDto>(tractionInTrainDtoList, link);
            return ResponseEntity.ok(tractionInTrainDtoList);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/{id}/tractions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> addTraction(@PathVariable int id,
                                                           @RequestBody @Valid TractionInTrainPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            int tractionId = DecodePath.decodeInteger(dto.getTractionUrl(), "tractions");
            Optional<Traction> traction = tractionRepository.findByIdAndOwnerId(tractionId, ownerId);
            if (traction.isPresent()) {
                TrainComposition trainComposition = optional.get();

                TractionInTrain tractionInTrain = new TractionInTrain();
                tractionInTrain.setTraction(traction.get());
                tractionInTrain.setPosition(dto.getPosition());
                tractionInTrain.setDriverIndication(dto.getDriverIndication());
                trainCompositionService.addTractionToTrain(trainComposition, tractionInTrain);
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

    @PutMapping(value = "/{id}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> moveTraction(@PathVariable int id,
                                                            @PathVariable int tractionId, @RequestParam("position") int position) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainComposition trainComposition = optional.get();
            trainCompositionService.moveTractionById(trainComposition, tractionId, position);
            TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                    .map(trainComposition);
            return ResponseEntity.ok(trainCompositionDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> removeTraction(@PathVariable int id,
                                                              @PathVariable int tractionId) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository
                .findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainComposition trainComposition = optional.get();
            trainComposition.removeTractionById(tractionId);
            tractionInTrainRepository.saveAll(trainComposition.getTractions());
            trainCompositionRepository.save(trainComposition);
            TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                    .map(trainComposition);
            return ResponseEntity.ok(trainCompositionDto);
        }
        return ResponseEntity.notFound().build();
    }

}
