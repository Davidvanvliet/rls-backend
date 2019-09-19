package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import nl.rls.composer.rest.dto.mapper.TrainActivityTypeDtoMapper;

@RestController
@RequestMapping("/api/v1/trainactivitytypes")
public class TrainActivityTypeController {
	@Autowired
	private TrainActivityTypeRepository trainActivityTypeRepository;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainActivityTypeDto> getTrainActivityType(@PathVariable Integer id) {
		Optional<TrainActivityType> optional = trainActivityTypeRepository.findById(id);
		if (optional.isPresent()) {
			TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(trainActivityTypeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainActivityTypeDto> getTrainActivityTypeByCode(@RequestParam("code") String code) {
		Optional<TrainActivityType> optional = trainActivityTypeRepository.findByCode(code);
		if (optional.isPresent()) {
			TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(trainActivityTypeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<TrainActivityTypeDto>> getAll() {
		Iterable<TrainActivityType> trainActivityTypeList = trainActivityTypeRepository.findAll();
		List<TrainActivityTypeDto> trainActivityTypeDtoList = new ArrayList<>();

		for (TrainActivityType trainActivityType : trainActivityTypeList) {
			trainActivityTypeDtoList.add(TrainActivityTypeDtoMapper.map(trainActivityType));
		}
		Link trainActivityTypesLink = linkTo(methodOn(TrainActivityTypeController.class).getAll()).withSelfRel();
		Resources<TrainActivityTypeDto> trainActivityTypes = new Resources<TrainActivityTypeDto>(trainActivityTypeDtoList, trainActivityTypesLink);
		return ResponseEntity.ok(trainActivityTypes);
	}

}
