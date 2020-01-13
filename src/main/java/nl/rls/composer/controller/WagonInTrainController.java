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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.domain.code.BrakeType;
import nl.rls.composer.repository.TrainCompositionRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.TrainCompositionDto;
import nl.rls.composer.rest.dto.WagonInTrainAddDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionDtoMapper;
import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;

@RestController
@RequestMapping(BaseURL.BASE_PATH+TrainCompositionController.PATH)
public class WagonInTrainController {
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private WagonRepository wagonRepository;
	@Autowired
	private TrainCompositionService trainCompositionService;
	@Autowired
	private TrainCompositionRepository trainCompositionRepository;

	@GetMapping(value = "/{id}/wagons", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<WagonInTrainDto>> getAllWagonInTrain(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainComposition> optional = trainCompositionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainComposition entity = optional.get();
			List<WagonInTrainDto> wagonInTrainDtoList = new ArrayList<WagonInTrainDto>();
			for (WagonInTrain wagonInTrain : entity.getWagons()) {
				WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
				wagonInTrainDtoList.add(wagonInTrainDto);
			}
//			Link link = linkTo(methodOn(WagonInTrainController.class).getAllWagonInTrain(id))
//					.withSelfRel();
//			Resources<WagonInTrainDto> wagonInTrainDtos = new Resources<WagonInTrainDto>(wagonInTrainDtoList, link);
			return ResponseEntity.ok(wagonInTrainDtoList);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/{id}/wagons/{wagonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WagonInTrainDto> getWagonInTrain(@PathVariable Integer id, @PathVariable Integer wagonId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainComposition> optional = trainCompositionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainComposition entity = optional.get();
			WagonInTrain wagonInTrain = entity.getWagonById(wagonId);
			WagonInTrainDto wagonInTrainDto = WagonInTrainDtoMapper.map(wagonInTrain);
			return ResponseEntity.ok(wagonInTrainDto);
		}
		return ResponseEntity.notFound().build();
	}


	@PostMapping(value = "/{id}/wagons", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionDto> addWagon(@PathVariable int id,
			@RequestBody WagonInTrainAddDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainComposition> optional = trainCompositionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			int wagonId = DecodePath.decodeInteger(dto.getWagonUrl(), "wagons");
			Optional<Wagon> wagon = wagonRepository.findByOwnerIdAndId(ownerId, wagonId);
			if (wagon.isPresent()) {
				TrainComposition trainComposition = optional.get();
				
				WagonInTrain wagonInTrain = new WagonInTrain();
				wagonInTrain.setWagon(wagon.get());
				wagonInTrain.setPosition(dto.getPosition());
				
				wagonInTrain.setBrakeType(BrakeType.G);
				/*
				 * set ...
				 */
				trainCompositionService.addWagonToTrain(trainComposition, wagonInTrain);
				TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
						.map(trainComposition);
				return ResponseEntity
						.created(linkTo(methodOn(TrainCompositionController.class)
								.getById(trainComposition.getId())).toUri())
						.body(trainCompositionDto);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "/{id}/wagons/{wagonInTrainId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionDto> moveWagon(@PathVariable int id,
			@PathVariable int wagonInTrainId, @RequestParam("position") int position) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainComposition> optional = trainCompositionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainComposition trainComposition = optional.get();
			trainCompositionService.moveWagonById(trainComposition, wagonInTrainId, position);
			TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
					.map(trainComposition);
			return ResponseEntity.accepted().body(trainCompositionDto);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "/{id}/wagons/{wagonInTrainId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionDto> removeWagon(@PathVariable int id,
			@PathVariable int wagonInTrainId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainComposition> optional = trainCompositionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainComposition trainComposition = optional.get();
			trainComposition.removeWagonById(wagonInTrainId);
//			wagonInTrainRepository.saveAll(trainCompositionJourneySection.getWagons());
			trainCompositionRepository.save(trainComposition);
			TrainCompositionDto trainCompositionDto = TrainCompositionDtoMapper
					.map(trainComposition);
			return ResponseEntity.ok(trainCompositionDto);
		}
		return ResponseEntity.notFound().build();
	}

}
