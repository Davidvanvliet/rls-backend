package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import nl.rls.composer.rest.dto.mapper.TrainActivityTypeDtoMapper;
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/trainactivitytypes")
public class TrainActivityTypeController {
    private final TrainActivityTypeRepository trainActivityTypeRepository;

    public TrainActivityTypeController(TrainActivityTypeRepository trainActivityTypeRepository) {
        this.trainActivityTypeRepository = trainActivityTypeRepository;
    }

    @GetMapping(value = "/{trainActivityTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainActivityTypeDto> getById(@PathVariable int trainActivityTypeId) {
        TrainActivityType trainActivityType = trainActivityTypeRepository.findById(trainActivityTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train activity type with id %d", trainActivityTypeId)));
        TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper.map(trainActivityType);
        return ResponseBuilder.ok()
                .data(trainActivityTypeDto)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TrainActivityTypeDto>> getAll(@RequestParam(value = "code", required = false) String code) {
        List<TrainActivityType> trainActivityTypes;
        if (code == null) {
            trainActivityTypes = trainActivityTypeRepository.findAll();
        } else {
            TrainActivityType trainActivityType = trainActivityTypeRepository.findByCode(code)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train activity type with code %s", code)));
            trainActivityTypes = Collections.singletonList(trainActivityType);
        }
        List<TrainActivityTypeDto> trainActivityTypeDtos = trainActivityTypes.stream()
                .map(TrainActivityTypeDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(trainActivityTypeDtos)
                .build();
    }

}
