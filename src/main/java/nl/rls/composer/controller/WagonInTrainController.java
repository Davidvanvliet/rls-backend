package nl.rls.composer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import nl.rls.composer.domain.*;
import nl.rls.composer.repository.DangerGoodsTypeRepository;
import nl.rls.composer.rest.dto.*;
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
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;

@RestController
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class WagonInTrainController {
    public static final String PATH = "/wagonintrains";
    private final SecurityContext securityContext;
    private final WagonRepository wagonRepository;
    private final TrainCompositionService trainCompositionService;
    private final TrainCompositionRepository trainCompositionRepository;
    private final DangerGoodsTypeRepository dangerGoodsTypeRepository;

    public WagonInTrainController(SecurityContext securityContext, WagonRepository wagonRepository, TrainCompositionService trainCompositionService, TrainCompositionRepository trainCompositionRepository, DangerGoodsTypeRepository dangerGoodsTypeRepository) {
        this.securityContext = securityContext;
        this.wagonRepository = wagonRepository;
        this.trainCompositionService = trainCompositionService;
        this.trainCompositionRepository = trainCompositionRepository;
        this.dangerGoodsTypeRepository = dangerGoodsTypeRepository;
    }

    @GetMapping(value = "/{trainCompositionId}/wagons", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<WagonInTrainDto>> getAllWagonInTrain(@PathVariable int trainCompositionId) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        List<WagonInTrainDto> wagonInTrainDtos = trainComposition.getWagons().stream()
                .map(WagonInTrainDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(wagonInTrainDtos)
                .build();
    }

    @GetMapping(value = "/{trainCompositionId}/wagons/{wagonId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<WagonInTrainDto> getWagonInTrain(@PathVariable int trainCompositionId, @PathVariable int wagonId) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        WagonInTrain wagonInTrain = trainComposition.getWagonById(wagonId);
        WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
        return ResponseBuilder.ok()
                .data(wagonInTrainDto)
                .build();
    }


    @PostMapping(value = "/{trainCompositionId}/wagons", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainCompositionDto> addWagon(@PathVariable int trainCompositionId,
                                                  @RequestBody @Valid WagonInTrainAddDto dto) {
        int ownerId = securityContext.getOwnerId();

        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        int wagonId = DecodePath.decodeInteger(dto.getWagonUrl(), "wagons");
        Wagon wagon = wagonRepository.findByIdAndOwnerId(wagonId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon with id %d", wagonId)));

        WagonInTrain wagonInTrain = new WagonInTrain();
        if (dto.getDangerGoodsInWagonPostDtos() != null) {
            for (DangerGoodsInWagonPostDto dangerGoodsInWagonPostDto : dto.getDangerGoodsInWagonPostDtos()) {
                int dangerGoodsTypeId = DecodePath.decodeInteger(dangerGoodsInWagonPostDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
                DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId).orElseThrow(() -> new EntityNotFoundException(String.format("Could not find dangerous goods type with id %d", dangerGoodsTypeId)));
                wagonInTrain.addDangerGoodsInWagon(new DangerGoodsInWagon(
                        dangerGoodsType,
                        wagonInTrain,
                        dangerGoodsInWagonPostDto.getDangerousGoodsWeight(),
                        dangerGoodsInWagonPostDto.getDangerousGoodsVolume())
                );
            }
        }
        wagonInTrain.setOwnerId(ownerId);
        wagonInTrain.setWagon(wagon);
        wagonInTrain.setPosition(dto.getPosition());
        wagonInTrain.setBrakeType(BrakeType.valueOf(dto.getBrakeType()));
        wagonInTrain.setTotalLoadWeight(dto.getTotalLoadWeight());
        trainCompositionService.addWagonToTrain(trainComposition, wagonInTrain);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                .map(trainComposition);
        return ResponseBuilder.created()
                .data(trainCompositionDto)
                .build();
    }

    @PutMapping(value = "/{trainCompositionId}/wagons/{wagonInTrainId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<TrainCompositionDto> moveWagon(@PathVariable int trainCompositionId,
                                                   @PathVariable int wagonInTrainId,
                                                   @RequestParam("position") int position) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        trainCompositionService.moveWagonById(trainComposition, wagonInTrainId, position);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                .map(trainComposition);
        return ResponseBuilder.accepted()
                .data(trainCompositionDto)
                .build();
    }

    @DeleteMapping(value = "/{trainCompositionId}/wagons/{wagonInTrainId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainCompositionDto> removeWagon(@PathVariable int trainCompositionId,
                                                     @PathVariable int wagonInTrainId) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        trainComposition.removeWagonById(wagonInTrainId);
        trainCompositionRepository.save(trainComposition);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
                .map(trainComposition);
        return ResponseBuilder.ok()
                .data(trainCompositionDto)
                .build();
    }

}
