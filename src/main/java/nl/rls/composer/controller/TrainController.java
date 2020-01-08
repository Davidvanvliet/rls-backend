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
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.Responsibility;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.ResponsibilityRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainCreateDto;
import nl.rls.composer.rest.dto.TrainDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionJourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "trains")
public class TrainController {
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ResponsibilityRepository responsibilityRepository;
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
	@Autowired
	private SecurityContext securityContext;

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TrainDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		System.out.println("TrainController " + ownerId);
		Iterable<Train> trainList = trainRepository.findByOwnerId(ownerId);
		System.out.println("TrainController " + ownerId);
		List<TrainDto> trainDtoList = new ArrayList<>();

		for (Train train : trainList) {
			trainDtoList.add(TrainDtoMapper.map(train));
		}
//		Link trainsLink = linkTo(methodOn(TrainController.class).getAll()).withSelfRel();
//		Resources<TrainDto> trains = new Resources<TrainDto>(trainDtoList, trainsLink);
		return ResponseEntity.ok(trainDtoList);
	}

	//
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainDto> getById(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainDto trainDto = TrainDtoMapper.map(optional.get());
			return ResponseEntity.ok(trainDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainDto> create(@RequestBody TrainCreateDto dto) {
		int ownerId = securityContext.getOwnerId();
		Train train = new Train();
		train.setOwnerId(ownerId);
		train.setOperationalTrainNumber(dto.getOperationalTrainNumber());
		train.setScheduledDateTimeAtTransfer(dto.getScheduledDateTimeAtTransfer());
		train.setScheduledTimeAtHandover(dto.getScheduledTimeAtHandover());
		/* ProRail = 0084 */
		List<Company> recipient = companyRepository.findByCode("0084");
		if (recipient.size() == 1) {
			train.setTransfereeIM(recipient.get(0));
		}

		Integer locationId = DecodePath.decodeInteger(dto.getTransferPoint(), "locations");
		Optional<Location> locationIdent = locationIdentRepository.findById(locationId);
		if (locationIdent.isPresent()) {
			train.setTransferPoint(locationIdent.get());
		}

		train = trainRepository.save(train);
		if (train != null) {
			TrainDto resultDto = TrainDtoMapper.map(train);
			return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
					.body(resultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainDto> update(@PathVariable Integer id, @RequestBody TrainCreateDto trainCreateDto) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			Train train = TrainDtoMapper.map(trainCreateDto);
			train.setOwnerId(ownerId);
			train = trainRepository.save(train);
			if (train != null) {
				TrainDto dto = TrainDtoMapper.map(train);

				return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
						.body(dto);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping(value = "{id}/journeysections/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TrainCompositionJourneySectionDto>> getAllJourneySections(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {

			Iterable<TrainCompositionJourneySection> sectionList = trainCompositionJourneySectionRepository
					.findByTrainAndOwnerId(optional.get(), ownerId);
			System.out.println("TrainCompositionJourneySectionController " + ownerId);
			List<TrainCompositionJourneySectionDto> journeySectionDtoList = new ArrayList<>();

			for (TrainCompositionJourneySection entity : sectionList) {
				journeySectionDtoList.add(TrainCompositionJourneySectionDtoMapper.map(entity));
			}
//			Link trainsLink = linkTo(methodOn(TrainController.class).getAllJourneySections(id)).withSelfRel();
//			Resources<TrainCompositionJourneySectionDto> trains = new Resources<TrainCompositionJourneySectionDto>(
//					journeySectionDtoList, trainsLink);
			return ResponseEntity.ok(journeySectionDtoList);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(value = "{id}/journeysections/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainDto> createSection(@PathVariable Integer id,
			@RequestBody TrainCompositionJourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (!optional.isPresent()) {
			ResponseEntity.notFound();
		}
		Train train = optional.get();
		TrainCompositionJourneySection trainCompositionJourneySection = new TrainCompositionJourneySection(ownerId);
		trainCompositionJourneySection.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
		trainCompositionJourneySection.setDangerousGoodsIndicator(dto.getDangerousGoodsIndicator() == 1);
		trainCompositionJourneySection.setExceptionalGaugingInd(dto.getExceptionalGaugingInd() == 1);
		trainCompositionJourneySection.setTrainType(dto.getTrainType());
		trainCompositionJourneySection.setTrainMaxSpeed(100);

		Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOrigin(), "locations");
		Optional<Location> optionalLocation = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optionalLocation.isPresent()) {
			trainCompositionJourneySection.setJourneySectionOrigin(optionalLocation.get());
		}

		locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestination(), "locations");
		optionalLocation = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optionalLocation.isPresent()) {
			trainCompositionJourneySection.setJourneySectionDestination(optionalLocation.get());
		}
		Responsibility responsibility = responsibilityRepository.findByOwnerId(ownerId);
		trainCompositionJourneySection.setResponsibilityActualSection(responsibility);
		trainCompositionJourneySection.setResponsibilityNextSection(responsibility);

		trainCompositionJourneySection = trainCompositionJourneySectionRepository.save(trainCompositionJourneySection);
		train.addJourneySection(trainCompositionJourneySection);
		train = trainRepository.save(train);

		if (train != null) {
			TrainDto resultDto = TrainDtoMapper.map(train);

			return ResponseEntity.created(linkTo(methodOn(TrainCompositionJourneySectionController.class)
					.getById(trainCompositionJourneySection.getId())).toUri()).body(resultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "{id}/journeysections/{sectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainDto> removeJourneysection(@PathVariable int id, @PathVariable int sectionId) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Train train = optional.get();
		TrainCompositionJourneySection journeySection = train.getJourneySectionById(sectionId);
		train.removeJourneySection(journeySection);
		train = trainRepository.save(train);
		if (train != null) {
			TrainDto resultDto = TrainDtoMapper.map(train);
			return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
					.body(resultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
