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
@RequestMapping(BaseURL.BASE_PATH + WagonInTrainController.PATH)
public class DangerGoodsInWagonController {
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private DangerGoodsTypeRepository dangerGoodsTypeRepository;
    @Autowired
    private WagonInTrainRepository wagonInTrainRepository;

    @GetMapping(value = "/{id}/dangergoods/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DangerGoodsInWagonDto>> getAllDangerGoodsInWagon(@PathVariable Integer id) {
        System.out.println("getAllDangerGoodsInWagon");
        Integer ownerId = securityContext.getOwnerId();
        Optional<WagonInTrain> optional = wagonInTrainRepository.findByIdAndOwnerId(id, ownerId);
        System.out.println("id: " + id + ", ownerId: " + ownerId + " optional: " + optional.get());
        if (optional.isPresent()) {
            WagonInTrain entity = optional.get();
            List<DangerGoodsInWagonDto> dtoList = new ArrayList<DangerGoodsInWagonDto>();
            for (DangerGoodsInWagon dangerGoodsInWagon : entity.getDangerGoodsInWagons()) {
                DangerGoodsInWagonDto dangerGoodsInWagonDto = DangerGoodsInWagonDtoMapper.map(dangerGoodsInWagon);
                dtoList.add(dangerGoodsInWagonDto);
            }
            return ResponseEntity.ok(dtoList);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{id}/dangergoods/{dangerGoodsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DangerGoodsInWagonDto> getDangerGoodsInWagon(@PathVariable Integer id,
                                                                       @PathVariable Integer dangerGoodsId) {
        int ownerId = securityContext.getOwnerId();
        Optional<WagonInTrain> optional = wagonInTrainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            WagonInTrain entity = optional.get();

            DangerGoodsInWagon dangerGoodsInWagon = entity.getDangerGoodsInWagonById(dangerGoodsId);
            DangerGoodsInWagonDto dangerGoodsInWagonDto = DangerGoodsInWagonDtoMapper.map(dangerGoodsInWagon);
            return ResponseEntity.ok(dangerGoodsInWagonDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/{id}/dangergoods/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WagonInTrainDto> postDangerGoods(@PathVariable int id,
                                                           @RequestBody DangerGoodsInWagonPostDto postDto) {
        int ownerId = securityContext.getOwnerId();
        Optional<WagonInTrain> optional = wagonInTrainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            WagonInTrain entity = optional.get();
            int dangerGoodsTypeId = DecodePath.decodeInteger(postDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
            Optional<DangerGoodsType> dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId);
            if (dangerGoodsType.isPresent()) {
                DangerGoodsInWagon dangerGoodsInWagon = new DangerGoodsInWagon();
                dangerGoodsInWagon.setDangerousGoodsWeight(postDto.getDangerousGoodsWeight());
                dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType.get());
                dangerGoodsInWagon.setWagonInTrain(entity);
                entity.addDangerGoodsInWagon(dangerGoodsInWagon);
                wagonInTrainRepository.save(entity);
                WagonInTrainDto dto = WagonInTrainDtoMapper.map(entity);
                return ResponseEntity
                        .created(linkTo(methodOn(WagonInTrainController.class)
                                .getWagonInTrain(entity.getTrainComposition().getId(), entity.getId())).toUri())
                        .body(dto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}/dangergoods/{dangerGoodsId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WagonInTrainDto> updateDangerGoodsInWagon(@PathVariable int id,
                                                                    @PathVariable int dangerGoodsId, @RequestBody DangerGoodsInWagonPostDto postDto) {
        int ownerId = securityContext.getOwnerId();
        Optional<WagonInTrain> optional = wagonInTrainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            WagonInTrain entity = optional.get();
            int dangerGoodsTypeId = DecodePath.decodeInteger(postDto.getDangerGoodsTypeUrl(), "dangergoodstypes");
            Optional<DangerGoodsType> dangerGoodsType = dangerGoodsTypeRepository.findById(dangerGoodsTypeId);
            if (dangerGoodsType.isPresent()) {
                DangerGoodsInWagon dangerGoodsInWagon = entity.getDangerGoodsInWagonById(dangerGoodsId);
                if (postDto.getDangerousGoodsWeight() != 0) {
                    dangerGoodsInWagon.setDangerousGoodsWeight(postDto.getDangerousGoodsWeight());
                }
                if (postDto.getDangerousGoodsVolume() != null) {
                    dangerGoodsInWagon.setDangerousGoodsVolume(postDto.getDangerousGoodsVolume());
                }
                dangerGoodsInWagon.setDangerGoodsType(dangerGoodsType.get());
                wagonInTrainRepository.save(entity);
                WagonInTrainDto dto = WagonInTrainDtoMapper.map(entity);
                return ResponseEntity.accepted().body(dto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}/dangergoods/{dangerGoodsId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WagonInTrainDto> deleteDangerGoodsInWagon(@PathVariable int id,
                                                                    @PathVariable int dangerGoodsId) {
        int ownerId = securityContext.getOwnerId();
        Optional<WagonInTrain> optional = wagonInTrainRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            WagonInTrain entity = optional.get();

            entity.removeDangerGoodsById(dangerGoodsId);
            // wagonInTrainRepository.saveAll(trainCompositionJourneySection.getWagons());
            wagonInTrainRepository.save(entity);
            WagonInTrainDto dto = WagonInTrainDtoMapper.map(entity);
            return ResponseEntity.accepted().body(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
