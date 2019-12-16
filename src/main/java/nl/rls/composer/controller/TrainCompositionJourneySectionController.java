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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public static final String PATH = "traincompositionjourneysections";
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
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
		Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOrigin(), "locationidents");
		Optional<Location> optional = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optional.isPresent()) {
			trainCompositionJourneySection.setJourneySectionOrigin(optional.get());
		}

		locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestination(), "locationidents");
		optional = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optional.isPresent()) {
			trainCompositionJourneySection.setJourneySectionDestination(optional.get());
		}
		trainCompositionJourneySection.setDangerousGoodsIndicator(false);
		trainCompositionJourneySection.setExceptionalGaugingInd(false);
		trainCompositionJourneySection.setTrainType(dto.getTrainType());
		trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
		TrainCompositionJourneySectionDto trainCompositionJourneySectionDto = TrainCompositionJourneySectionDtoMapper
				.map(trainCompositionJourneySection);
		return ResponseEntity
				.created(linkTo(methodOn(TrainCompositionJourneySectionController.class)
						.getById(trainCompositionJourneySection.getId())).toUri())
				.body(trainCompositionJourneySectionDto);
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
