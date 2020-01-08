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

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionPostDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionJourneySectionDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+TrainCompositionJourneySectionController.PATH)
public class TrainCompositionJourneySectionController {
	public static final String PATH = "journeysections";
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
	@Autowired
	private SecurityContext securityContext;


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
			Optional<Location> locationIdent = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
			if (locationIdent.isPresent()) {
				trainCompositionJourneySection.setJourneySectionOrigin(locationIdent.get());
			}

			locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestination(), "locationidents");
			locationIdent = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
			if (locationIdent.isPresent()) {
				trainCompositionJourneySection.setJourneySectionDestination(locationIdent.get());
			}
			trainCompositionJourneySection.setTrainType(dto.getTrainType());
			trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
			TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.accepted().body(trainCompositionJourneySectionDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


}
