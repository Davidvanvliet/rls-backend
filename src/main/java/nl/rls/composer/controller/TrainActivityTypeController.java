package nl.rls.composer.controller;

import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import nl.rls.composer.rest.dto.mapper.TrainActivityTypeDtoMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/trainactivitytypes")
public class TrainActivityTypeController {
    private final TrainActivityTypeRepository trainActivityTypeRepository;

    public TrainActivityTypeController(TrainActivityTypeRepository trainActivityTypeRepository) {
        this.trainActivityTypeRepository = trainActivityTypeRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainActivityTypeDto> getById(@PathVariable Integer id) {
        Optional<TrainActivityType> optional = trainActivityTypeRepository.findById(id);
        if (optional.isPresent()) {
            TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper
                    .map(optional.get());
            return ResponseEntity.ok(trainActivityTypeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrainActivityTypeDto> getByCode(@RequestParam("code") String code) {
        Optional<TrainActivityType> optional = trainActivityTypeRepository.findByCode(code);
        if (optional.isPresent()) {
            TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper
                    .map(optional.get());
            return ResponseEntity.ok(trainActivityTypeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainActivityTypeDto>> getAll() {
        Iterable<TrainActivityType> trainActivityTypeList = trainActivityTypeRepository.findAll();
        List<TrainActivityTypeDto> trainActivityTypeDtoList = new ArrayList<>();

        for (TrainActivityType trainActivityType : trainActivityTypeList) {
            trainActivityTypeDtoList.add(TrainActivityTypeDtoMapper.map(trainActivityType));
        }
//		Link trainActivityTypesLink = linkTo(methodOn(TrainActivityTypeController.class).getAll()).withSelfRel();
//		Resources<TrainActivityTypeDto> trainActivityTypes = new Resources<TrainActivityTypeDto>(trainActivityTypeDtoList, trainActivityTypesLink);
        return ResponseEntity.ok(trainActivityTypeDtoList);
    }

}
