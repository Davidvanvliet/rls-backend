package nl.rls.composer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.code.TractionMode;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TractionModeRepository;
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

@RestController
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class TractionInTrainController {
    private final SecurityContext securityContext;
    private final TractionRepository tractionRepository;
    private final TractionInTrainRepository tractionInTrainRepository;
    private final TrainCompositionService trainCompositionService;
    private final TrainCompositionRepository trainCompositionRepository;
    private final TractionModeRepository tractionModeRepository;


    public TractionInTrainController(SecurityContext securityContext, TractionRepository tractionRepository, TractionInTrainRepository tractionInTrainRepository, TrainCompositionService trainCompositionService, TrainCompositionRepository trainCompositionRepository, TractionModeRepository tractionModeRepository) {
        this.securityContext = securityContext;
        this.tractionRepository = tractionRepository;
        this.tractionInTrainRepository = tractionInTrainRepository;
        this.trainCompositionService = trainCompositionService;
        this.trainCompositionRepository = trainCompositionRepository;
        this.tractionModeRepository = tractionModeRepository;
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
        int tractionModeId = DecodePath.decodeInteger(dto.getTractionMode(), "tractionmodes");
        Optional<TractionMode> tractionMode = tractionModeRepository.findById(tractionModeId);
        if (tractionMode.isPresent()) {
        	tractionInTrain.setTractionMode(tractionMode.get());
        }

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
