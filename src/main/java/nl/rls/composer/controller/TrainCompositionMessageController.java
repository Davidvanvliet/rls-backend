package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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

import nl.rls.ci.aa.domain.Owner;
import nl.rls.ci.aa.repository.OwnerRepository;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.LocationIdent;
import nl.rls.composer.domain.Responsibility;
import nl.rls.composer.domain.TrainCompositionJourneySection;
import nl.rls.composer.domain.TrainCompositionMessage;
import nl.rls.composer.domain.TrainRunningData;
import nl.rls.composer.domain.code.MessageType;
import nl.rls.composer.domain.message.MessageStatus;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.JourneySectionRepository;
import nl.rls.composer.repository.LocationIdentRepository;
import nl.rls.composer.repository.ResponsibilityRepository;
import nl.rls.composer.repository.TrainCompositionJourneySectionRepository;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import nl.rls.composer.repository.TrainRunningDataRepository;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionDto;
import nl.rls.composer.rest.dto.TrainCompositionJourneySectionPostDto;
import nl.rls.composer.rest.dto.TrainCompositionMessageDto;
import nl.rls.composer.rest.dto.TrainCompositionMessagePostDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionMessageDtoMapper;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

@RestController
@RequestMapping("/api/v1/traincompositionmessages")
public class TrainCompositionMessageController {
	@Autowired
	private TrainCompositionMessageRepository trainCompositionMessageRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ResponsibilityRepository responsibilityRepository;
	@Autowired
	private LocationIdentRepository locationIdentRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private TrainCompositionJourneySectionRepository trainCompositionJourneySectionRepository;
	@Autowired
	private JourneySectionRepository journeySectionRepository;
	@Autowired
	private TrainRunningDataRepository trainRunningDataRepository;

	@Autowired
	private SecurityContext securityContext;

	@GetMapping("/hello")
	public ResponseEntity<String> getHello() {
		return ResponseEntity.ok("Hello world");
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<TrainCompositionMessageDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		System.out.println("TrainCompositionMessageController " + ownerId);
		Iterable<TrainCompositionMessage> trainCompositionMessageList = trainCompositionMessageRepository
				.findByOwnerId(ownerId);
		System.out.println("TrainCompositionMessageController " + ownerId);
		List<TrainCompositionMessageDto> trainCompositionMessageDtoList = new ArrayList<>();

		for (TrainCompositionMessage trainCompositionMessage : trainCompositionMessageList) {
			trainCompositionMessageDtoList.add(TrainCompositionMessageDtoMapper.map(trainCompositionMessage));
		}
		Link trainCompositionMessagesLink = linkTo(methodOn(TrainCompositionMessageController.class).getAll())
				.withSelfRel();
		Resources<TrainCompositionMessageDto> trainCompositionMessages = new Resources<TrainCompositionMessageDto>(
				trainCompositionMessageDtoList, trainCompositionMessagesLink);
		return ResponseEntity.ok(trainCompositionMessages);
	}

	//
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionMessageDto> getById(@PathVariable int id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionMessage> optional = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionMessageDto trainCompositionMessageDto = TrainCompositionMessageDtoMapper
					.map(optional.get());
			return ResponseEntity.ok(trainCompositionMessageDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getTcmIdent(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionMessage> optional = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			StringWriter sw = new StringWriter();
			System.out.println(optional.get());
			nl.rls.xml.tcm.TrainCompositionMessage tcm = TrainCompositionMessageXmlMapper.map(optional.get());
			try {
				// File file = new File("train_composition_message.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(nl.rls.xml.tcm.TrainCompositionMessage.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				// output pretty printed
				// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
				// jaxbMarshaller.marshal(tcm, file);
				jaxbMarshaller.marshal(tcm, System.out);
				jaxbMarshaller.marshal(tcm, sw);
			} catch (JAXBException e) {
				e.printStackTrace();
				//
			}
			return ResponseEntity.ok(sw.toString());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionMessageDto> create(@RequestBody TrainCompositionMessagePostDto dto) {
		int ownerId = securityContext.getOwnerId();
		TrainCompositionMessage trainCompositionMessage = new TrainCompositionMessage();
		trainCompositionMessage.setOwnerId(ownerId);
		trainCompositionMessage.setMessageIdentifier(dto.getMessageIdentifier());
		trainCompositionMessage.setMessageDateTime(new Date());
		trainCompositionMessage.setMessageType(MessageType.TRAIN_COMPOSITION_MESSAGE.code());
		trainCompositionMessage.setMessageTypeVersion(MessageType.TRAIN_COMPOSITION_MESSAGE.version());
		trainCompositionMessage.setMessageStatus(MessageStatus.creation.getValue());
		trainCompositionMessage.setSenderReference("senderReference");

		Optional<Company> recipient = companyRepository.findByCode("0084");
		if (recipient.isPresent()) {
			trainCompositionMessage.setRecipient(recipient.get());
			trainCompositionMessage.setTransfereeIM(recipient.get());
		}

		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (owner.isPresent()) {
			Optional<Company> sender = companyRepository.findByCode(owner.get().getCode());
			if (sender.isPresent()) {
				trainCompositionMessage.setSender(sender.get());
			}
		}

		Integer locationId = DecodePath.decodeInteger(dto.getTransferPoint(), "locationidents");
		Optional<LocationIdent> locationIdent = locationIdentRepository.findById(locationId);
		if (locationIdent.isPresent()) {
			trainCompositionMessage.setTransferPoint(locationIdent.get());
		}

		trainCompositionMessage = trainCompositionMessageRepository.save(trainCompositionMessage);
		if (trainCompositionMessage != null) {
			TrainCompositionMessageDto resultDto = TrainCompositionMessageDtoMapper.map(trainCompositionMessage);

			return ResponseEntity.created(
					linkTo(methodOn(TrainCompositionMessageController.class).getById(trainCompositionMessage.getId()))
							.toUri())
					.body(resultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionMessageDto> update(@PathVariable Integer id,
			@RequestBody TrainCompositionMessagePostDto trainCompositionMessagePostDto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionMessage> optional = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionMessage trainCompositionMessage = TrainCompositionMessageDtoMapper
					.map(trainCompositionMessagePostDto);
			trainCompositionMessage.setOwnerId(ownerId);
			trainCompositionMessage = trainCompositionMessageRepository.save(trainCompositionMessage);
			if (trainCompositionMessage != null) {
				TrainCompositionMessageDto dto = TrainCompositionMessageDtoMapper.map(trainCompositionMessage);

				return ResponseEntity.created(linkTo(
						methodOn(TrainCompositionMessageController.class).getById(trainCompositionMessage.getId()))
								.toUri())
						.body(dto);
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(value = "{id}/journeysections/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainCompositionMessageDto> createSection(@PathVariable Integer id, 
			@RequestBody TrainCompositionJourneySectionPostDto dto) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionMessage> tcm = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
		if (!tcm.isPresent()) {
			ResponseEntity.notFound();
		}
		TrainCompositionJourneySection entity = new TrainCompositionJourneySection(ownerId);
		entity.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());
		TrainRunningData trainRunningData = new TrainRunningData(ownerId);
		trainRunningData.setDangerousGoodsIndicator(dto.getDangerousGoodsIndicator() == 1);
		trainRunningData.setExceptionalGaugingInd(dto.getExceptionalGaugingInd() == 1);
		trainRunningData.setTrainType(dto.getTrainType());
		trainRunningData.setTrainMaxSpeed(80);
		trainRunningData.setTrain(entity);
		entity.setTrainRunningData(trainRunningData);
		entity.setLivestockOrPeopleIndicator(dto.getLivestockOrPeopleIndicator());

		JourneySection journeySection = new JourneySection(ownerId);
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
		Responsibility responsibility = responsibilityRepository.findByOwnerId(ownerId);
		journeySection.setResponsibilityActualSection(responsibility);
		journeySection.setResponsibilityNextSection(responsibility);
		journeySectionRepository.save(journeySection);
		entity.setJourneySection(journeySection);

		entity.setTrainRunningData(trainRunningData);
		entity = trainCompositionJourneySectionRepository.save(entity);
		tcm.get().getTrainCompositionJourneySection().add(entity);
		TrainCompositionMessage trainCompositionMessage = trainCompositionMessageRepository.save(tcm.get());
		
		if (trainCompositionMessage != null) {
			TrainCompositionMessageDto resultDto = TrainCompositionMessageDtoMapper.map(trainCompositionMessage);

			return ResponseEntity.created(
					linkTo(methodOn(TrainCompositionJourneySectionController.class).getById(entity.getId()))
							.toUri())
					.body(resultDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
