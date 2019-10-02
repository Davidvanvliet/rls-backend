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
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.LocationIdent;
import nl.rls.composer.domain.Locomotive;
import nl.rls.composer.domain.LocomotiveInTrain;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.TrainRunningData;
import nl.rls.composer.domain.Wagon;
import nl.rls.composer.domain.WagonInTrain;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.LocomotiveInTrainRepository;
import nl.rls.composer.repository.LocomotiveRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.TrainRunningDataRepository;
import nl.rls.composer.repository.WagonInTrainRepository;
import nl.rls.composer.repository.WagonRepository;
import nl.rls.composer.rest.dto.LocomotiveAddDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionPostDto;
import nl.rls.composer.rest.dto.WagonInTrainAddDto;
import nl.rls.composer.rest.dto.WagonInTrainDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionJourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.WagonInTrainDtoMapper;

@RestController
@RequestMapping("/api/v1/traincompositionjourneysections")
public class TrainCompositionJourneySectionController {
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
	@Autowired
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private TrainRunningDataRepository trainRunningDataRepository;
	@Autowired
	private LocomotiveRepository locomotiveRepository;
	@Autowired
	private LocomotiveInTrainRepository locomotiveInTrainRepository;
	@Autowired
	private WagonRepository wagonRepository;
	@Autowired
	private WagonInTrainRepository wagonInTrainRepository;
	@Autowired
	private SecurityContext securityContext;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<TrainCompositionJourneySectionDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		System.out.println("TrainCompositionJourneySectionController " + ownerId);
		Iterable<TrainCompositionJourneySection> trainCompositionJourneySectionList = trainCompositionJourneySectionRepository
				.findByOwnerId(ownerId);
		System.out.println("TrainCompositionJourneySectionController " + ownerId);
		List<TrainCompositionJourneySectionDto> trainCompositionJourneySectionDtoList = new ArrayList<>();

		for (TrainCompositionJourneySection trainCompositionJourneySection : trainCompositionJourneySectionList) {
			trainCompositionJourneySectionDtoList
					.add(TrainCompositionJourneySectionDtoMapper.map(trainCompositionJourneySection));
		}
		Link trainCompositionJourneySectionsLink = linkTo(
				methodOn(TrainCompositionJourneySectionController.class).getAll()).withSelfRel();
		Resources<TrainCompositionJourneySectionDto> trainCompositionJourneySections = new Resources<TrainCompositionJourneySectionDto>(
				trainCompositionJourneySectionDtoList, trainCompositionJourneySectionsLink);
		return ResponseEntity.ok(trainCompositionJourneySections);
	}

	//
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> getById(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> create(
			@RequestBody TrainCompositionJourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		TrainCompositionJourneySection trainCompositionJourneySection = TrainCompositionJourneySectionDtoMapper
				.map(dto);
		trainCompositionJourneySection.setOwnerId(ownerId);
		JourneySection journeySection = new JourneySection();
		journeySection.setOwnerId(ownerId);
		Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOrigin(), "locationidents");
		Optional<LocationIdent> optional = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optional.isPresent()) {
			journeySection.setJourneySectionOrigin(optional.get());
		}

		locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestination(), "locationidents");
		optional = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optional.isPresent()) {
			journeySection.setJourneySectionDestination(optional.get());
		}
		journeySectionRepository.save(journeySection);
		trainCompositionJourneySection.setJourneySection(journeySection);
		TrainRunningData trainRunningData = new TrainRunningData();
		trainRunningData.setDangerousGoodsIndicator(false);
		trainRunningData.setExceptionalGaugingInd(false);
		trainRunningData.setTrainType(dto.getTrainType());
		trainRunningDataRepository.save(trainRunningData);
		trainCompositionJourneySection.setTrainRunningData(trainRunningData);
		trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
		TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
				.map(trainCompositionJourneySection);
		return ResponseEntity
				.created(linkTo(methodOn(TrainCompositionJourneySectionController.class)
						.getById(trainCompositionJourneySection.getId())).toUri())
				.body(trainCompositionJourneySectionDto);
	}

	@PostMapping(value = "{id}/locomotives", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> addLocomotive(@PathVariable int id,
			@RequestBody LocomotiveAddDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			int locomotiveId = DecodePath.decodeInteger(dto.getLocomotive(), "locomtives");
			Optional<Locomotive> locomotive = locomotiveRepository.findByIdAndOwnerId(locomotiveId, ownerId);
			if (locomotive.isPresent()) {
				TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
				LocomotiveInTrain locomotiveInTrain = new LocomotiveInTrain();
				locomotiveInTrain.setLocomotive(locomotive.get());
				locomotiveInTrain.setTractionPositionInTrain(dto.getTractionPositionInTrain());
				locomotiveInTrain.setDriverIndication(dto.getDriverIndication());
				locomotiveInTrainRepository.save(locomotiveInTrain);
				trainCompositionJourneySection.getLocomotives().add(locomotiveInTrain);
				trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
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

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> update(@PathVariable int id,
			@RequestBody TrainCompositionJourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
			trainCompositionJourneySection.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
			Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOrigin(), "locationidents");
			Optional<LocationIdent> locationIdent = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
			if (locationIdent.isPresent()) {
				trainCompositionJourneySection.getJourneySection().setJourneySectionOrigin(locationIdent.get());
			}

			locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestination(), "locationidents");
			locationIdent = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
			if (locationIdent.isPresent()) {
				trainCompositionJourneySection.getJourneySection().setJourneySectionDestination(locationIdent.get());
			}
			journeySectionRepository.save(trainCompositionJourneySection.getJourneySection());

			trainCompositionJourneySection.getTrainRunningData().setTrainType(dto.getTrainType());
			trainRunningDataRepository.save(trainCompositionJourneySection.getTrainRunningData());
			trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.accepted().body(trainCompositionJourneySectionDto);
		} else {
			return ResponseEntity.notFound().build();
		}
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

	@GetMapping(value = "{id}/wagons/", produces = MediaType.APPLICATION_JSON_VALUE)
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
			Link link = linkTo(methodOn(TrainCompositionJourneySectionController.class).getAllWagonInTrain(id))
					.withSelfRel();
			Resources<WagonInTrainDto> wagonInTrainDtos = new Resources<WagonInTrainDto>(wagonInTrainDtoList, link);
			return ResponseEntity.ok(wagonInTrainDtos);
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
			int wagonId = DecodePath.decodeInteger(dto.getWagon(), "wagons");
			Optional<Wagon> wagon = wagonRepository.findByIdAndOwnerId(wagonId, ownerId);
			if (wagon.isPresent()) {
				TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
				
				WagonInTrain wagonInTrain = new WagonInTrain();
				wagonInTrain.setWagon(wagon.get());
				wagonInTrain.setWagonTrainPosition(dto.getWagonTrainPosition());
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

	@DeleteMapping(value = "{id}/wagons/{wagonId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionJourneySectionDto> removeWagon(@PathVariable int id,
			@PathVariable int wagonId) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionJourneySection> optional = trainCompositionJourneySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionJourneySection trainCompositionJourneySection = optional.get();
			trainCompositionJourneySection.removeWagonById(wagonId);
			wagonInTrainRepository.saveAll(trainCompositionJourneySection.getWagons());
			trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		}
		return ResponseEntity.notFound().build();
	}
}
