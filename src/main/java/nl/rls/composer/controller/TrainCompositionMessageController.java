package nl.rls.composer.controller;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.url.BaseURL;
import nl.rls.ci.url.DecodePath;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.code.MessageType;
import nl.rls.composer.domain.message.MessageStatus;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.TrainCompositionMessageCreateDto;
import nl.rls.composer.rest.dto.TrainCompositionMessageDto;
import nl.rls.composer.rest.dto.mapper.TrainCompositionMessageDtoMapper;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/traincompositionmessages")
public class TrainCompositionMessageController {
    private final TrainCompositionMessageRepository trainCompositionMessageRepository;
    private final CompanyRepository companyRepository;
    private final TrainRepository trainRepository;

    private final SecurityContext securityContext;

    public TrainCompositionMessageController(TrainCompositionMessageRepository trainCompositionMessageRepository, CompanyRepository companyRepository, TrainRepository trainRepository, SecurityContext securityContext) {
        this.trainCompositionMessageRepository = trainCompositionMessageRepository;
        this.companyRepository = companyRepository;
        this.trainRepository = trainRepository;
        this.securityContext = securityContext;
    }

    //TODO - remove this endpoint
    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello world");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TrainCompositionMessageDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        System.out.println("TrainCompositionMessageController " + ownerId);
        Iterable<TrainCompositionMessage> trainCompositionMessageList = trainCompositionMessageRepository
                .findByOwnerId(ownerId);
        System.out.println("TrainCompositionMessageController " + ownerId);
        List<TrainCompositionMessageDto> trainCompositionMessageDtoList = new ArrayList<>();

        for (TrainCompositionMessage trainCompositionMessage : trainCompositionMessageList) {
            trainCompositionMessageDtoList.add(TrainCompositionMessageDtoMapper.map(trainCompositionMessage));
        }
//		Link trainCompositionMessagesLink = linkTo(methodOn(TrainCompositionMessageController.class).getAll())
//				.withSelfRel();
//		Resources<TrainCompositionMessageDto> trainCompositionMessages = new Resources<TrainCompositionMessageDto>(
//				trainCompositionMessageDtoList, trainCompositionMessagesLink);
        return ResponseEntity.ok(trainCompositionMessageDtoList);
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
            info.taf_jsg.schemes.TrainCompositionMessage tcm = TrainCompositionMessageXmlMapper.map(optional.get());
            try {
                // File file = new File("train_composition_message.xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
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
    public ResponseEntity<TrainCompositionMessageDto> create(@RequestBody @Valid TrainCompositionMessageCreateDto dto) {
        int ownerId = securityContext.getOwnerId();
        TrainCompositionMessage trainCompositionMessage = new TrainCompositionMessage();
        trainCompositionMessage.setOwnerId(ownerId);
        trainCompositionMessage.setMessageIdentifier(UUID.randomUUID().toString());
        trainCompositionMessage.setMessageDateTime(new Date());
        trainCompositionMessage.setMessageType(MessageType.TRAIN_COMPOSITION_MESSAGE.code());
        trainCompositionMessage.setMessageTypeVersion(MessageType.TRAIN_COMPOSITION_MESSAGE.version());
        trainCompositionMessage.setMessageStatus(MessageStatus.creation.getValue());
        trainCompositionMessage.setSenderReference(UUID.randomUUID().toString());

        /* ProRail = 0084 */
        List<Company> recipient = companyRepository.findByCode("0084");
        if (recipient.size() == 1) {
            trainCompositionMessage.setRecipient(recipient.get(0));
        }

        List<Company> sender = companyRepository.findByCode("9001");
        if (sender.size() == 1) {
            trainCompositionMessage.setSender(sender.get(0));
        }

        Integer trainId = DecodePath.decodeInteger(dto.getTrain(), "trains");
        Optional<Train> optional = trainRepository.findByIdAndOwnerId(trainId, ownerId);
        if (optional.isPresent()) {
            trainCompositionMessage.setTrain(optional.get());
            optional.get().getTrainCompositionMessages().add(trainCompositionMessage);
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
                                                             @RequestBody @Valid TrainCompositionMessageCreateDto trainCompositionMessageCreateDto) {
        int ownerId = securityContext.getOwnerId();
        Optional<TrainCompositionMessage> optional = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
        if (optional.isPresent()) {
            TrainCompositionMessage trainCompositionMessage = TrainCompositionMessageDtoMapper
                    .map(trainCompositionMessageCreateDto);
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

}
