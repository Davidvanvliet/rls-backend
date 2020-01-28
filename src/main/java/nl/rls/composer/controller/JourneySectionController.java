package nl.rls.composer.controller;

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
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainCompositionCloneDto;
import nl.rls.composer.rest.dto.TrainCompositionPostDto;
import nl.rls.composer.rest.dto.TrainDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;

@RestController
@RequestMapping(BaseURL.BASE_PATH + JourneySectionController.PATH)
public class JourneySectionController {
	public static final String PATH = "journeysections";
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private TrainCompositionService trainCompositionService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> getById(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySectionDto trainCompositionJourneySectionDto = JourneySectionDtoMapper.map(optional.get());
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		} else {
			return ResponseEntity.status(422).build();
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> update(@PathVariable int id, @RequestBody JourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySection journeySection = optional.get();
			Integer locationId = DecodePath.decodeInteger(dto.getJourneySectionOriginUrl(), "locations");
			Optional<Location> location = locationRepository.findByLocationPrimaryCode(locationId);
			if (location.isPresent()) {
				journeySection.setJourneySectionOrigin(location.get());
			}

			locationId = DecodePath.decodeInteger(dto.getJourneySectionDestinationUrl(), "locations");
			location = locationRepository.findByLocationPrimaryCode(locationId);
			if (location.isPresent()) {
				journeySection.setJourneySectionDestination(location.get());
			}
			journeySectionRepository.save(journeySection);
			JourneySectionDto journeySectionDto = JourneySectionDtoMapper.map(journeySection);
			return ResponseEntity.accepted().body(journeySectionDto);
		} else {
			return ResponseEntity.status(422).build();
		}
	}

	@PutMapping(value = "/{id}/traincomposition", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> putTrainCompositionToJourneySection(@PathVariable Integer id,
			@RequestBody TrainCompositionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository.findByIdAndOwnerId(id, ownerId);
		if (!optional.isPresent()) {
			ResponseEntity.status(422).build();
		}
		JourneySection journeySection = optional.get();
		if (journeySection.getTrainComposition() == null) {
			journeySection.setTrainComposition(new TrainComposition(ownerId));
			journeySection.getTrainComposition().setJourneySection(journeySection);
		}
		journeySection.getTrainComposition().setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
		journeySection.getTrainComposition().setBrakeType(dto.getBrakeType());
		journeySection.getTrainComposition().setBrakeWeight(dto.getBrakeWeight());
		journeySection.getTrainComposition().setTrainMaxSpeed(dto.getTrainMaxSpeed());
		journeySection.getTrainComposition().setMaxAxleWeight(dto.getMaxAxleWeight());
		journeySection = journeySectionRepository.save(journeySection);
		if (journeySection != null) {
			JourneySectionDto resultDto = JourneySectionDtoMapper.map(journeySection);
			return ResponseEntity.accepted().body(resultDto);
		} else {
			return ResponseEntity.status(422).build();
		}
	}

	@ApiOperation(value = "Copies or clones a complete TrainComposition including tractions and wagons.")
	@PutMapping(value = "/{id}/clone/")
	public ResponseEntity<TrainDto> copyComposition(@PathVariable Integer id,
			@RequestBody TrainCompositionCloneDto dto) {
		int ownerId = securityContext.getOwnerId();
		JourneySection toJourneySection = null;
		JourneySection fromJourneySection = null;
		Integer fromSectionId = DecodePath.decodeInteger(dto.getJourneySectionUrl(), "journeysections");
		Optional<JourneySection> optionalJourneySection = journeySectionRepository.findByIdAndOwnerId(fromSectionId,
				ownerId);
		if (optionalJourneySection.isPresent()) {
			fromJourneySection = optionalJourneySection.get();
		} else {
			return ResponseEntity.status(422).build();
		}

		optionalJourneySection = journeySectionRepository.findByIdAndOwnerId(id, ownerId);
		if (optionalJourneySection.isPresent()) {
			toJourneySection = optionalJourneySection.get();
		} else {
			return ResponseEntity.status(422).build();
		}
		TrainComposition newTrainComposition = trainCompositionService
				.copyTrainComposition(fromJourneySection.getTrainComposition());
		toJourneySection.setTrainComposition(newTrainComposition);
		journeySectionRepository.save(toJourneySection);
		Optional<Train> optionalTrain = trainRepository.findByIdAndOwnerId(toJourneySection.getTrain().getId(), ownerId);
		TrainDto resultDto = TrainDtoMapper.map(optionalTrain.get());
		return ResponseEntity.ok(resultDto);
	}

}
