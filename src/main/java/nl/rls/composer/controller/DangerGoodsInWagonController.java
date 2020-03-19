package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.DangerGoodsInWagon;
import nl.rls.composer.domain.DangerGoodsType;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.repository.DangerGoodsTypeRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
import nl.rls.composer.rest.dto.DangerGoodsInWagonDto;
import nl.rls.composer.rest.dto.DangerGoodsInWagonPostDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import nl.rls.composer.rest.dto.mapper.DangerGoodsInWagonDtoMapper;
import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + WagonInTrainController.PATH)
public class DangerGoodsInWagonController {
    private final SecurityContext securityContext;
    private final DangerGoodsTypeRepository dangerGoodsTypeRepository;
    private final WagonInTrainRepository wagonInTrainRepository;

    public DangerGoodsInWagonController(SecurityContext securityContext, DangerGoodsTypeRepository dangerGoodsTypeRepository, WagonInTrainRepository wagonInTrainRepository) {
        this.securityContext = securityContext;
        this.dangerGoodsTypeRepository = dangerGoodsTypeRepository;
        this.wagonInTrainRepository = wagonInTrainRepository;
    }

    @GetMapping(value = "/{wagonInTrainId}/dangergoods", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<DangerGoodsInWagonDto>> getAllDangerGoodsInWagon(@PathVariable int wagonInTrainId) {
        int ownerId = securityContext.getOwnerId();
        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
        List<DangerGoodsInWagonDto> dangerGoodsInWagonDtoList = wagonInTrain.getDangerGoodsInWagons().stream()
                .map(DangerGoodsInWagonDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(dangerGoodsInWagonDtoList)
                .build();
    }

    @GetMapping(value = "/{wagonInTrainId}/dangergoods/{dangerGoodsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<DangerGoodsInWagonDto> getDangerGoodsInWagon(@PathVariable int wagonInTrainId,
                                                                 @PathVariable int dangerGoodsId) {
        int ownerId = securityContext.getOwnerId();
        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
        DangerGoodsInWagon dangerGoodsInWagon = wagonInTrain.getDangerGoodsInWagonById(dangerGoodsId);
        DangerGoodsInWagonDto dangerGoodsInWagonDto = DangerGoodsInWagonDtoMapper.map(dangerGoodsInWagon);
        return ResponseBuilder.ok()
                .data(dangerGoodsInWagonDto)
                .build();
    }

    @PostMapping(value = "/{wagonInTrainId}/dangergoods", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<WagonInTrainDto> postDangerGoods(@PathVariable int wagonInTrainId,
                                                     @RequestBody DangerGoodsInWagonPostDto postDto) {
        int ownerId = securityContext.getOwnerId();
        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
        int dangerGoodsTypeId = DecodePath.decodeInteger(postDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
        DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find danger good type with id %d", dangerGoodsTypeId)));
        DangerGoodsInWagon dangerGoodsInWagon = new DangerGoodsInWagon();
        dangerGoodsInWagon.setDangerousGoodsWeight(postDto.getDangerousGoodsWeight());
        dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType);
        dangerGoodsInWagon.setWagonInTrain(wagonInTrain);
        wagonInTrain.addDangerGoodsInWagon(dangerGoodsInWagon);
        wagonInTrainRepository.save(wagonInTrain);
        WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
        return ResponseBuilder.created()
                .data(wagonInTrainDto)
                .build();
    }

    @PutMapping(value = "/{wagonInTrainId}/dangergoods/{dangerGoodsId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<WagonInTrainDto> updateDangerGoodsInWagon(@PathVariable int wagonInTrainId,
                                                              @PathVariable int dangerGoodsId,
                                                              @RequestBody DangerGoodsInWagonPostDto postDto) {
        int ownerId = securityContext.getOwnerId();
        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
        int dangerGoodsTypeId = DecodePath.decodeInteger(postDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
        DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find danger good type with id %d", dangerGoodsTypeId)));
        DangerGoodsInWagon dangerGoodsInWagon = wagonInTrain.getDangerGoodsInWagonById(dangerGoodsId);
        if (postDto.getDangerousGoodsWeight() != 0) {
            dangerGoodsInWagon.setDangerousGoodsWeight(postDto.getDangerousGoodsWeight());
        }
        if (postDto.getDangerousGoodsVolume() != null) {
            dangerGoodsInWagon.setDangerousGoodsVolume(postDto.getDangerousGoodsVolume());
        }
        dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType);
        wagonInTrainRepository.save(wagonInTrain);
        WagonInTrainDto dto = WagonInTrainDtoMapper.map(wagonInTrain);
        return ResponseBuilder.accepted()
                .data(dto)
                .build();
    }

    @DeleteMapping(value = "/{wagonInTrainId}/dangergoods/{dangerGoodsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<WagonInTrainDto> deleteDangerGoodsInWagon(@PathVariable int wagonInTrainId,
                                                              @PathVariable int dangerGoodsId) {
        int ownerId = securityContext.getOwnerId();
        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));

        wagonInTrain.removeDangerGoodsById(dangerGoodsId);
        wagonInTrainRepository.save(wagonInTrain);
        WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
        return ResponseBuilder.accepted()
                .data(wagonInTrainDto)
                .build();
    }
}
