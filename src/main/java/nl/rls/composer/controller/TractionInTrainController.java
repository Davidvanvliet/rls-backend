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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/{trainCompositionId}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TractionInTrainDto> getTractionInTrain(@PathVariable int trainCompositionId,
                                                           @PathVariable Integer tractionId) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        TractionInTrain tractionInTrain = trainComposition.getTractionById(tractionId);
        TractionInTrainDto tractionInTrainDto = TractionInTrainDtoMapper.map(tractionInTrain);
        return ResponseBuilder.ok()
                .data(tractionInTrainDto)
                .build();
    }

    @GetMapping(value = "/{trainCompositionId}/tractions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TractionInTrainDto>> getAllTractionInTrain(@PathVariable int trainCompositionId) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        List<TractionInTrainDto> tractionInTrainDtoList = trainComposition.getTractions().stream()
                .map(TractionInTrainDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(tractionInTrainDtoList)
                .build();

    }

    @PostMapping(value = "/{trainCompositionId}/tractions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainCompositionDto> addTraction(@PathVariable int trainCompositionId,
                                                     @RequestBody @Valid TractionInTrainPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        int tractionId = DecodePath.decodeInteger(dto.getTractionUrl(), "tractions");

        Traction traction = tractionRepository.findByIdAndOwnerId(tractionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find traction with id %d", tractionId)));

        TractionInTrain tractionInTrain = new TractionInTrain();
        tractionInTrain.setTraction(traction);
        tractionInTrain.setPosition(dto.getPosition());
        tractionInTrain.setDriverIndication(dto.getDriverIndication());
        trainCompositionService.addTractionToTrain(trainComposition, tractionInTrain);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                .map(trainComposition);
        return ResponseBuilder.created()
                .data(trainCompositionDto)
                .build();

    }

    @PutMapping(value = "/{trainCompositionId}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainCompositionDto> moveTraction(@PathVariable int trainCompositionId,
                                                      @PathVariable int tractionId,
                                                      @RequestParam("position") int position) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        trainCompositionService.moveTractionById(trainComposition, tractionId, position);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                .map(trainComposition);
        return ResponseBuilder.ok()
                .data(trainCompositionDto)
                .build();
    }

    @DeleteMapping(value = "/{trainCompositionId}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainCompositionDto> removeTraction(@PathVariable int trainCompositionId,
                                                        @PathVariable int tractionId) {
        int ownerId = securityContext.getOwnerId();

        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        trainComposition.removeTractionById(tractionId);
        tractionInTrainRepository.saveAll(trainComposition.getTractions());
        trainCompositionRepository.save(trainComposition);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                .map(trainComposition);
        return ResponseBuilder.ok()
                .data(trainCompositionDto)
                .build();
    }

}
