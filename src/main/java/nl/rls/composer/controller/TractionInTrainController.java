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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Traction;
import nl.rls.composer.domain.TractionInTrain;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.repository.TractionInTrainRepository;
import nl.rls.composer.repository.TractionRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.rest.dto.TractionInTrainAddDto;
import nl.rls.composer.rest.dto.TractionInTrainDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.mapper.TractionInTrainDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainCompositionJourneySectionDtoMapper;
import nl.rls.composer.service.TrainCompositionJourneySectionService;

@RestController
@RequestMapping(BaseURL.BASE_PATH+TrainCompositionJourneySectionController.PATH)
public class TractionInTrainController {
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private TractionRepository tractionRepository;
	@Autowired
	private TractionInTrainRepository tractionInTrainRepository;
	@Autowired
	private TrainCompositionJourneySectionService trainCompositionJourneySectionService;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;

	@GetMapping(value = "{id}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TractionInTrainDto> getTractionInTrain(@PathVariable Integer id, @PathVariable Integer tractionId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection entity = optional.get();
			TractionInTrain tractionInTrain = entity.getTractionById(tractionId);
			TractionInTrainDto tractionInTrainDto = TractionInTrainDtoMapper.map(tractionInTrain);
			return ResponseEntity.ok(tractionInTrainDto);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "{id}/tractions/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<TractionInTrainDto>> getAllTractionInTrain(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection entity = optional.get();
			List<TractionInTrainDto> tractionInTrainDtoList = new ArrayList<TractionInTrainDto>();
			for (TractionInTrain tractionInTrain : entity.getTractions()) {
				TractionInTrainDto tractionInTrainDto = TractionInTrainDtoMapper.map(tractionInTrain);
				tractionInTrainDtoList.add(tractionInTrainDto);
			}
			Link link = linkTo(methodOn(TractionInTrainController.class).getAllTractionInTrain(id))
					.withSelfRel();
			Resources<TractionInTrainDto> tractionInTrainDtos = new Resources<TractionInTrainDto>(tractionInTrainDtoList, link);
			return ResponseEntity.ok(tractionInTrainDtos);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(value = "{id}/tractions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> addTraction(@PathVariable int id,
			@RequestBody TractionInTrainAddDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			int tractionId = DecodePath.decodeInteger(dto.getTraction(), "tractions");
			Optional<Traction> traction = tractionRepository.findByIdAndOwnerId(tractionId, ownerId);
			if (traction.isPresent()) {
				TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
				
				TractionInTrain tractionInTrain = new TractionInTrain();
				tractionInTrain.setTraction(traction.get());
				tractionInTrain.setPosition(dto.getPosition());
				tractionInTrain.setDriverIndication(dto.getDriverIndication());
				trainCompositionJourneySectionService.addTractionToTrain(trainCompositionJourneySection, tractionInTrain);
				TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
						.map(trainCompositionJourneySection);
				return ResponseEntity
						.created(linkTo(methodOn(TrainCompositionJourneySectionController.class)
								.getById(trainCompositionJourneySection.getId())).toUri())
						.body(trainCompositionJourneySectionDto);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "{id}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> moveTraction(@PathVariable int id,
			@PathVariable int tractionInTrainId, @RequestParam("position") int position) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
			trainCompositionJourneySectionService.moveTractionById(trainCompositionJourneySection, tractionInTrainId, position);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "{id}/tractions/{tractionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> removeTraction(@PathVariable int id,
			@PathVariable int tractionId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
			trainCompositionJourneySection.removeTractionById(tractionId);
			tractionInTrainRepository.saveAll(trainCompositionJourneySection.getTractions());
			trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		}
		return ResponseEntity.notFound().build();
	}

}
