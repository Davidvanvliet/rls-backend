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
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainCompositionPostDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+JourneySectionController.PATH)
public class JourneySectionController {
	public static final String PATH = "journeysections";
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private SecurityContext securityContext;


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> getById(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySectionDto trainCompositionJourneySectionDto = JourneySectionDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(trainCompositionJourneySectionDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> update(@PathVariable int id,
			@RequestBody JourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository
				.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			JourneySection trainCompositionJourneySection = optional.get();
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
			journeySectionRepository.save(trainCompositionJourneySection);
			JourneySectionDto trainCompositionJourneySectionDto = JourneySectionDtoMapper
					.map(trainCompositionJourneySection);
			return ResponseEntity.accepted().body(trainCompositionJourneySectionDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(value = "/{id}/traincomposition", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JourneySectionDto> putTrainCompositionToJourneySection(@PathVariable Integer id,
			@RequestBody TrainCompositionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<JourneySection> optional = journeySectionRepository.findByIdAndOwnerId(id, ownerId);
		if (!optional.isPresent()) {
			ResponseEntity.notFound();
		}
		JourneySection journeySection = optional.get();
		TrainComposition trainComposition = new TrainComposition(ownerId);
		trainComposition.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
		journeySection.setTrainComposition(trainComposition);
		journeySection = journeySectionRepository.save(journeySection);
		if (journeySection != null) {
			JourneySectionDto resultDto = JourneySectionDtoMapper.map(journeySection);
			return ResponseEntity.accepted().body(resultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


}
