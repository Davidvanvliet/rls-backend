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
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.WagonLoad;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
import nl.rls.composer.repository.WagonLoadRepository;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.WagonInTrainAddDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionJourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;
import nl.rls.composer.service.TrainCompositionJourneySectionService;

@RestController
@RequestMapping(BaseURL.BASE_PATH+TrainCompositionJourneySectionController.PATH)
public class WagonInTrainController {
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private WagonLoadRepository wagonLoadRepository;
	@Autowired
	private WagonInTrainRepository wagonInTrainRepository;
	@Autowired
	private TrainCompositionJourneySectionService trainCompositionJourneySectionService;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;

	@GetMapping(value = "{id}/wagons", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<WagonInTrainDto>> getAllWagonInTrain(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection entity = optional.get();
			List<WagonInTrainDto> wagonInTrainDtoList = new ArrayList<WagonInTrainDto>();
			for (WagonInTrain wagonInTrain : entity.getWagons()) {
				WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
				wagonInTrainDtoList.add(wagonInTrainDto);
			}
			Link link = linkTo(methodOn(WagonInTrainController.class).getAllWagonInTrain(id))
					.withSelfRel();
			Resources<WagonInTrainDto> wagonInTrainDtos = new Resources<WagonInTrainDto>(wagonInTrainDtoList, link);
			return ResponseEntity.ok(wagonInTrainDtos);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "{id}/wagons/{wagonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonInTrainDto> getWagonInTrain(@PathVariable Integer id, @PathVariable Integer wagonId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection entity = optional.get();
			WagonInTrain wagonInTrain = entity.getWagonById(wagonId);
			WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
			return ResponseEntity.ok(wagonInTrainDto);
		}
		return ResponseEntity.notFound().build();
	}


	@PostMapping(value = "{id}/wagons", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> addWagon(@PathVariable int id,
			@RequestBody WagonInTrainAddDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			int wagonLoadId = DecodePath.decodeInteger(dto.getWagonLoad(), "wagonloads");
			Optional<WagonLoad> wagonLoad = wagonLoadRepository.findByIdAndOwnerId(wagonLoadId, ownerId);
			if (wagonLoad.isPresent()) {
				TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
				
				WagonInTrain wagonInTrain = new WagonInTrain();
				wagonInTrain.setWagonLoad(wagonLoad.get());
				wagonInTrain.setPosition(dto.getPosition());
				trainCompositionJourneySectionService.addWagonToTrain(trainCompositionJourneySection, wagonInTrain);
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

	@PutMapping(value = "{id}/wagons/{wagonInTrainId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> moveWagon(@PathVariable int id,
			@PathVariable int wagonInTrainId, @RequestParam("position") int position) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
			trainCompositionJourneySectionService.moveWagonById(trainCompositionJourneySection, wagonInTrainId, position);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "{id}/wagons/{wagonInTrainId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> removeWagon(@PathVariable int id,
			@PathVariable int wagonInTrainId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
			trainCompositionJourneySection.removeWagonById(wagonInTrainId);
//			wagonInTrainRepository.saveAll(trainCompositionJourneySection.getWagons());
			trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		}
		return ResponseEntity.notFound().build();
	}

}
