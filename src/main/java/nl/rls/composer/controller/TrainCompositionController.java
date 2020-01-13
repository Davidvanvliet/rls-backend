package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.TrainCompositionPostDto;
import nl.rls.composer.rest.dto.TrainDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;

@RestController
@RequestMapping(BaseURL.BASE_PATH + TrainCompositionController.PATH)
public class TrainCompositionController {
	public static final String PATH = "traincompositions";
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private TrainCompositionRepository trainCompositionRepository;
	@Autowired
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private TrainCompositionService trainCompositionService;
	@Autowired
	private SecurityContext securityContext;

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

	@ApiOperation(value = "Copies or clones a complete TrainComposition including tractions and wagons.")
	@GetMapping(value = "/{id}/clone/journeysection/{sectionId}")
	public ResponseEntity<TrainDto> copySection(@PathVariable Integer id, @PathVariable Integer sectionId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainComposition> optional = trainCompositionRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainComposition trainComposition = optional.get();
			Optional<JourneySection> optionalJourneySection = journeySectionRepository.findByIdAndOwnerId(sectionId,
					ownerId);
			if (optionalJourneySection.isPresent()) {
				JourneySection journeySection = optionalJourneySection.get();
				TrainComposition newTrainComposition = trainCompositionService.copyTrainComposition(trainComposition);
				journeySection.setTrainComposition(newTrainComposition);
				journeySectionRepository.save(journeySection);
				Optional<Train> optionalTrain = trainRepository.findByIdAndOwnerId(journeySection.getTrain().getId(),
						ownerId);
				TrainDto resultDto = TrainDtoMapper.map(optionalTrain.get());
				return ResponseEntity
						.created(linkTo(methodOn(TrainController.class).getById(optionalTrain.get().getId())).toUri())
						.body(resultDto);

			}
		}
		return ResponseEntity.notFound().build();

	}

}
