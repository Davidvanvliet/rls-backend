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
import nl.rls.util.Response;
import nl.rls.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<TrainCompositionMessageDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        List<TrainCompositionMessage> trainCompositionMessageList = trainCompositionMessageRepository.findByOwnerId(ownerId);
        List<TrainCompositionMessageDto> trainCompositionMessageDtoList = trainCompositionMessageList.stream()
                .map(TrainCompositionMessageDtoMapper::map)
                .collect(Collectors.toList());
        return ResponseBuilder.ok()
                .data(trainCompositionMessageDtoList)
                .build();
    }

    @GetMapping(value = "/{trainCompositionMessageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<TrainCompositionMessageDto> getById(@PathVariable int trainCompositionMessageId) {
        int ownerId = securityContext.getOwnerId();
        TrainCompositionMessage trainCompositionMessage = trainCompositionMessageRepository.findByIdAndOwnerId(trainCompositionMessageId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train composition message with id %d", trainCompositionMessageId)));
        TrainCompositionMessageDto trainCompositionMessageDto = TrainCompositionMessageDtoMapper
                .map(trainCompositionMessage);
        return ResponseBuilder.ok()
                .data(trainCompositionMessageDto)
                .build();
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
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainCompositionMessageDto> create(@RequestBody @Valid TrainCompositionMessageCreateDto dto) {
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
        Train train = trainRepository.findByIdAndOwnerId(trainId, ownerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find train with id %d", trainId)));
        trainCompositionMessage.setTrain(train);

        trainCompositionMessage = trainCompositionMessageRepository.save(trainCompositionMessage);
        TrainCompositionMessageDto resultDto = TrainCompositionMessageDtoMapper.map(trainCompositionMessage);

        return ResponseBuilder.created()
                .data(resultDto)
                .build();

    }

    @PutMapping(value = "/{trainCompositionMessageId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<TrainCompositionMessageDto> update(@PathVariable int trainCompositionMessageId,
                                                       @RequestBody @Valid TrainCompositionMessageCreateDto trainCompositionMessageCreateDto) {
        int ownerId = securityContext.getOwnerId();

        if (!trainCompositionMessageRepository.existsByIdAndOwnerId(trainCompositionMessageId, ownerId)) {
            throw new EntityNotFoundException(String.format("Could not find train composition message with id %d", trainCompositionMessageId));
        }
        TrainCompositionMessage trainCompositionMessage = TrainCompositionMessageDtoMapper.map(trainCompositionMessageCreateDto);
        trainCompositionMessage.setOwnerId(ownerId);
        trainCompositionMessage.setId(trainCompositionMessageId);
        trainCompositionMessage = trainCompositionMessageRepository.save(trainCompositionMessage);
        TrainCompositionMessageDto dto = TrainCompositionMessageDtoMapper.map(trainCompositionMessage);
        return ResponseBuilder.created()
                .data(dto)
                .build();
    }

}
