package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.code.TrainActivityType;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.TrainActivityTypeRepository;
import nl.rls.composer.rest.dto.ActivityInTrainAddDto;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.TrainActivityTypeDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainActivityTypeDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+JourneySectionController.PATH)
public class ActivityInTrainController {
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private TrainActivityTypeRepository trainActivityTypeRepository;
	@Autowired
	private JourneySectionRepository journeySectionRepository;

	@GetMapping(value = "{id}/activities/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainActivityTypeDto> getActivityInTrain(@PathVariable Integer id, @PathVariable Integer activityId) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySection entity = optional.get();
			TrainActivityType trainActivityType = entity.getActivityById(activityId);
			TrainActivityTypeDto dto = TrainActivityTypeDtoMapper.map(trainActivityType);
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "{id}/activities/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TrainActivityTypeDto>> getAllActivityInTrain(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySection entity = optional.get();
			List<TrainActivityTypeDto> trainActivityTypeDtoList = new ArrayList<TrainActivityTypeDto>();
			for (TrainActivityType trainActivityType : entity.getActivities()) {
				TrainActivityTypeDto trainActivityTypeDto = TrainActivityTypeDtoMapper.map(trainActivityType);
				trainActivityTypeDtoList.add(trainActivityTypeDto);
			}
//			Link link = linkTo(methodOn(ActivityInTrainController.class).getAllActivityInTrain(id))
//					.withSelfRel();
//			Resources<TrainActivityTypeDto> dtos = new Resources<TrainActivityTypeDto>(trainActivityTypeDtoList, link);
			return ResponseEntity.ok(trainActivityTypeDtoList);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "{id}/activities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> addActivity(@PathVariable int id,
			@RequestBody ActivityInTrainAddDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			int trainActivityTypeId = DecodePath.decodeInteger(dto.getTrainActivityType(), "trainactivitytypes");
			Optional<TrainActivityType> trainActivityType = trainActivityTypeRepository.findById(trainActivityTypeId);
			if (trainActivityType.isPresent()) {
				JourneySection entity = optional.get();
				entity.addActivity(trainActivityType.get());
				journeySectionRepository.save(entity);
				JourneySectionDto trainCompositionJourneySectionDto = JourneySectionDtoMapper
						.map(entity);
				return ResponseEntity
						.created(linkTo(methodOn(JourneySectionController.class)
								.getById(entity.getId())).toUri())
						.body(trainCompositionJourneySectionDto);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "{id}/activities/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> removeActivity(@PathVariable int id,
			@PathVariable int activityId) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySection journeySection = optional.get();
			journeySection.removeActivityById(activityId);
			journeySectionRepository.save(journeySection);
			JourneySectionDto journeySectionDto = JourneySectionDtoMapper
					.map(journeySection);
			return ResponseEntity.ok(journeySectionDto);
		}
		return ResponseEntity.notFound().build();
	}

}
