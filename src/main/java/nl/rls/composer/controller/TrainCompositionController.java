package nl.rls.composer.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.auth.config.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.TrainCompositionPostDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;

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

    @GetMapping(value = "/{trainCompositionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('read:traincomposition')")
    public Response<TrainCompositionDto> getById(@PathVariable int trainCompositionId) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper.map(trainComposition);
        return ResponseBuilder.ok()
                .data(trainCompositionDto)
                .build();
    }

    @PutMapping(value = "/{trainCompositionId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasPermission('write:traincomposition')")
    public Response<TrainCompositionDto> update(@PathVariable int trainCompositionId,
                                                @RequestBody @Valid TrainCompositionPostDto dto) {
        int ownerId = securityContext.getOwnerId();
        TrainComposition trainComposition = trainCompositionRepository.findByIdAndOwnerId(trainCompositionId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition with id %d", trainCompositionId)));
        trainComposition.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
        trainCompositionRepository.save(trainComposition);
        TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper.map(trainComposition);
        return ResponseBuilder.accepted()
                .data(trainCompositionDto)
                .build();
    }


}
