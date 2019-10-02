package nl.rls.composer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.composer.domain.ActivityInTrain;
import nl.rls.composer.domain.TrainRunningData;
import nl.rls.composer.repository.TrainRunningDataRepository;
import nl.rls.composer.rest.dto.ActivityInTrainDto;
import nl.rls.composer.rest.dto.TrainRunningDataDto;
import nl.rls.composer.rest.dto.mapper.ActivityInTrainDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainRunningDataDtoMapper;

@RestController
@RequestMapping("/api/v1/trainrunningdata")
public class TrainRunningDataController {
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private TrainRunningDataRepository trainRunningDataRepository;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainRunningDataDto> getById(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainRunningData> optional = trainRunningDataRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainRunningDataDto trainRunningDataDto = TrainRunningDataDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(trainRunningDataDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "{id}/activity/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ActivityInTrainDto> getActivityInTrain(@PathVariable Integer id, @PathVariable Integer activityId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainRunningData> optional = trainRunningDataRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainRunningData entity = optional.get();
			ActivityInTrain activityInTrain = entity.getActivityById(activityId);
			ActivityInTrainDto dto = ActivityInTrainDtoMapper.map(activityInTrain);
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}

}
