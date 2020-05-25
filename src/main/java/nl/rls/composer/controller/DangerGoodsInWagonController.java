//package nl.rls.composer.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.persistence.EntityNotFoundException;
//import javax.validation.Valid;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import nl.rls.ci.aa.security.SecurityContext;
//import nl.rls.ci.url.BaseURL;
//import nl.rls.ci.url.DecodePath;
//import nl.rls.composer.domain.DangerGoodsInWagon;
//import nl.rls.composer.domain.DangerGoodsType;
//import nl.rls.composer.domain.WagonInTrain;
//import nl.rls.composer.repository.DangerGoodsTypeRepository;
//import nl.rls.composer.repository.WagonInTrainRepository;
//import nl.rls.composer.rest.dto.DangerGoodsInWagonDto;
//import nl.rls.composer.rest.dto.DangerGoodsInWagonPostDto;
//import nl.rls.composer.rest.dto.WagonInTrainDto;
//import nl.rls.composer.rest.dto.mapper.DangerGoodsInWagonDtoMapper;
//import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;
//import nl.rls.util.Response;
//import nl.rls.util.ResponseBuilder;
//
//@RestController
//@RequestMapping(BaseURL.BASE_PATH + "/wagons")
//public class DangerGoodsInWagonController {
//    private final SecurityContext securityContext;
//    private final DangerGoodsTypeRepository dangerGoodsTypeRepository;
//    private final WagonInTrainRepository wagonInTrainRepository;
//
//    public DangerGoodsInWagonController(SecurityContext securityContext, DangerGoodsTypeRepository dangerGoodsTypeRepository, WagonInTrainRepository wagonInTrainRepository) {
//        this.securityContext = securityContext;
//        this.dangerGoodsTypeRepository = dangerGoodsTypeRepository;
//        this.wagonInTrainRepository = wagonInTrainRepository;
//    }
//
//    @GetMapping(value = "/{wagonInTrainId}/dangergoods", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Response<List<DangerGoodsInWagonDto>> getAllDangerGoodsInWagon(@PathVariable int wagonInTrainId) {
//        int ownerId = securityContext.getOwnerId();
//        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
//        List<DangerGoodsInWagonDto> dangerGoodsInWagonDtoList = wagonInTrain.getDangerGoodsInWagons().stream()
//                .map(DangerGoodsInWagonDtoMapper::map)
//                .collect(Collectors.toList());
//        return ResponseBuilder.ok()
//                .data(dangerGoodsInWagonDtoList)
//                .build();
//    }
//
//    @GetMapping(value = "/{wagonInTrainId}/dangergoods/{dangerGoodsId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Response<DangerGoodsInWagonDto> getDangerGoodsInWagon(@PathVariable int wagonInTrainId,
//                                                                 @PathVariable int dangerGoodsId) {
//        int ownerId = securityContext.getOwnerId();
//        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
//        DangerGoodsInWagon dangerGoodsInWagon = wagonInTrain.getDangerGoodsInWagonById(dangerGoodsId);
//        DangerGoodsInWagonDto dangerGoodsInWagonDto = DangerGoodsInWagonDtoMapper.map(dangerGoodsInWagon);
//        return ResponseBuilder.ok()
//                .data(dangerGoodsInWagonDto)
//                .build();
//    }
//
//    @PostMapping(value = "/{wagonInTrainId}/dangergoods", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public Response<List<DangerGoodsInWagonDto>> postDangerGoods(@PathVariable int wagonInTrainId,
//                                                     @RequestBody @Valid DangerGoodsInWagonPostDto postDto) {
//        int ownerId = securityContext.getOwnerId();
//        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
//        int dangerGoodsTypeId = DecodePath.decodeInteger(postDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
//        DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find danger good type with id %d", dangerGoodsTypeId)));
//        DangerGoodsInWagon dangerGoodsInWagon = new DangerGoodsInWagon();
//        dangerGoodsInWagon.setDangerousGoodsWeight(postDto.getDangerousGoodsWeight());
//        dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType);
//        dangerGoodsInWagon.setWagonInTrain(wagonInTrain);
//        wagonInTrain.addDangerGoodsInWagon(dangerGoodsInWagon);
//        WagonInTrain postedWagonInTrain = wagonInTrainRepository.save(wagonInTrain);
//        return ResponseBuilder.created()
//                .data(postedWagonInTrain.getDangerGoodsInWagons())
//                .build();
//    }
//
//    @PutMapping(value = "/{wagonInTrainId}/dangergoods/{dangerGoodsId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Response<List<DangerGoodsInWagonDto>> updateDangerGoodsInWagon(@PathVariable int wagonInTrainId,
//                                                              @PathVariable int dangerGoodsId,
//                                                              @RequestBody @Valid DangerGoodsInWagonPostDto postDto) {
//        int ownerId = securityContext.getOwnerId();
//        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
//        int dangerGoodsTypeId = DecodePath.decodeInteger(postDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
//        DangerGoodsType dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find danger good type with id %d", dangerGoodsTypeId)));
//        DangerGoodsInWagon dangerGoodsInWagon = wagonInTrain.getDangerGoodsInWagonById(dangerGoodsId);
//        if (postDto.getDangerousGoodsWeight() != 0) {
//            dangerGoodsInWagon.setDangerousGoodsWeight(postDto.getDangerousGoodsWeight());
//        }
//        if (postDto.getDangerousGoodsVolume() != null) {
//            dangerGoodsInWagon.setDangerousGoodsVolume(postDto.getDangerousGoodsVolume());
//        }
//        dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType);
//        WagonInTrain postedWagonInTrain = wagonInTrainRepository.save(wagonInTrain);
//        List<DangerGoodsInWagonDto> dangerGoodsInWagonDtos = postedWagonInTrain.getDangerGoodsInWagons().stream().map(DangerGoodsInWagonDtoMapper::map).collect(Collectors.toList());
//        return ResponseBuilder.accepted()
//                .data(dangerGoodsInWagonDtos)
//                .build();
//    }
//
//    @DeleteMapping(value = "/{wagonInTrainId}/dangergoods/{dangerGoodsId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Response<List<DangerGoodsInWagonDto>> deleteDangerGoodsInWagon(@PathVariable int wagonInTrainId,
//                                                              @PathVariable int dangerGoodsId) {
//        int ownerId = securityContext.getOwnerId();
//        WagonInTrain wagonInTrain = wagonInTrainRepository.findByIdAndOwnerId(wagonInTrainId, ownerId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find wagon in train with id %d", wagonInTrainId)));
//
//        wagonInTrain.removeDangerGoodsById(dangerGoodsId);
//        wagonInTrainRepository.save(wagonInTrain);
//        WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
//        return ResponseBuilder.accepted()
//                .data(wagonInTrainDto)
//                .build();
//    }
//}
