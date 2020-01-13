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

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Location;
import nl.rls.composer.domain.Responsibility;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainComposition;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.ResponsibilityRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.JourneySectionDto;
import nl.rls.composer.rest.dto.JourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainCreateDto;
import nl.rls.composer.rest.dto.TrainDto;
import nl.rls.composer.rest.dto.mapper.JourneySectionDtoMapper;
import nl.rls.composer.rest.dto.mapper.TrainDtoMapper;
import nl.rls.composer.service.TrainCompositionService;

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
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	TrainCompositionService trainCompositionService;
	@Autowired
	private SecurityContext securityContext;

	@ApiOperation(value = "Gets a complete Train object")
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
		train.setTrainType(dto.getTrainType());

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
	public ResponseEntity<List<JourneySectionDto>> getAllJourneySections(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {

			Iterable<JourneySection> sectionList = journeySectionRepository
					.findByTrainAndOwnerId(optional.get(), ownerId);
			System.out.println("TrainCompositionJourneySectionController " + ownerId);
			List<JourneySectionDto> journeySectionDtoList = new ArrayList<>();

			for (JourneySection entity : sectionList) {
				journeySectionDtoList.add(JourneySectionDtoMapper.map(entity));
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
	public ResponseEntity<TrainDto> postJourneySection(@PathVariable Integer id,
			@RequestBody JourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (!optional.isPresent()) {
			ResponseEntity.notFound();
		}
		Train train = optional.get();
		JourneySection journeySection = new JourneySection(ownerId);
		Integer locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionOrigin(), "locations");
		Optional<Location> optionalLocation = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optionalLocation.isPresent()) {
			journeySection.setJourneySectionOrigin(optionalLocation.get());
		}

		locationIdentId = DecodePath.decodeInteger(dto.getJourneySectionDestination(), "locations");
		optionalLocation = locationIdentRepository.findByLocationPrimaryCode(locationIdentId);
		if (optionalLocation.isPresent()) {
			journeySection.setJourneySectionDestination(optionalLocation.get());
		}
		Responsibility responsibility = responsibilityRepository.findByOwnerId(ownerId);
		journeySection.setResponsibilityActualSection(responsibility);
		journeySection.setResponsibilityNextSection(responsibility);

		journeySection = journeySectionRepository.save(journeySection);
		train.addJourneySection(journeySection);
		train = trainRepository.save(train);

		if (train != null) {
			TrainDto resultDto = TrainDtoMapper.map(train);

			return ResponseEntity.created(linkTo(methodOn(JourneySectionController.class)
					.getById(journeySection.getId())).toUri()).body(resultDto);
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
		JourneySection journeySection = train.getJourneySectionById(sectionId);
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

	@ApiOperation(value = "Copies or clones a complete JourneySection including activities, tractions and wagons.")
	@GetMapping(value = "{id}/journeysections/{sectionId}/clone")
	public ResponseEntity<TrainDto> copySection(@PathVariable Integer id,
			@PathVariable Integer sectionId) {
		int ownerId = securityContext.getOwnerId();
		Optional<Train> optional = trainRepository.findByIdAndOwnerId(id, ownerId);
		if (!optional.isPresent()) {
			ResponseEntity.notFound();
		}
		Train train = optional.get();
		JourneySection journeySection = train.getJourneySectionById(sectionId);
		TrainComposition newTrainComposition = trainCompositionService.copyTrainComposition(journeySection.getTrainComposition());
		journeySection.setTrainComposition(newTrainComposition);
		train = trainRepository.save(train);
		TrainDto resultDto = TrainDtoMapper.map(train);
		return ResponseEntity.created(linkTo(methodOn(TrainController.class).getById(train.getId())).toUri())
				.body(resultDto);

	}
}
