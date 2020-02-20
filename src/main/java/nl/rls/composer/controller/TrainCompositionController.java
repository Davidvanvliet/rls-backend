package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.TrainCompositionPostDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class TrainCompositionController {
    public static final String PATH = "/traincompositions";
    private final TrainCompositionRepository trainCompositionRepository;
    private final SecurityContext securityContext;

    public TrainCompositionController(TrainCompositionRepository trainCompositionRepository, SecurityContext securityContext) {
        this.trainCompositionRepository = trainCompositionRepository;
        this.securityContext = securityContext;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> getById(@PathVariable int id) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper.map(optional.get());
            return ResponseEntity.ok(trainCompositionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainCompositionDto> update(@PathVariable int id, @RequestBody TrainCompositionPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainComposition> optional = trainCompositionRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainComposition trainComposition = optional.get();
            trainComposition.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
            trainCompositionRepository.save(trainComposition);
            TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper.map(trainComposition);
            return ResponseEntity.accepted().body(trainCompositionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
