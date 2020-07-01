package nl.rls.ci.service;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.CustomMessageStatus;
import nl.rls.ci.domain.LiTechnicalAck;
import nl.rls.ci.domain.UICMessageDto;
import nl.rls.ci.domain.dto.UicSendMessageDto;
import nl.rls.ci.domain.mapper.CustomMessageStatusMapper;
import nl.rls.composer.domain.Company;
import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.code.MessageType;
import nl.rls.composer.domain.message.MessageStatus;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.repository.CompanyRepository;
import nl.rls.composer.repository.TrainRepository;
import nl.rls.composer.rest.dto.TrainDto;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;
import org.dozer.DozerBeanMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.persistence.EntityNotFoundException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
public class CiService {
    private final SecurityContext securityContext;
    private final TrainRepository trainRepository;
    private final CompanyRepository companyRepository;
    private final DozerBeanMapper dozerBeanMapper;

    public CiService(SecurityContext securityContext, TrainRepository trainRepository, CompanyRepository companyRepository, DozerBeanMapper dozerBeanMapper) {
        this.securityContext = securityContext;
        this.trainRepository = trainRepository;
        this.companyRepository = companyRepository;
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public TrainDto sendMessageToCi(Integer trainId) {
        String tempRandomUUID = UUID.randomUUID().toString();
        Date now = new Date();
        int ownerId = securityContext.getOwnerId();
        Train train = trainRepository.findByIdAndOwnerId(trainId, ownerId).orElseThrow(EntityNotFoundException::new);
        TrainCompositionMessage trainCompositionMessage = new TrainCompositionMessage();
        trainCompositionMessage.setTrain(train);
        trainCompositionMessage.setMessageDateTime(now);
        trainCompositionMessage.setOwnerId(ownerId);
        trainCompositionMessage.setMessageStatus(MessageStatus.creation.getValue());
        trainCompositionMessage.setMessageType(MessageType.TRAIN_COMPOSITION_MESSAGE.code());
        trainCompositionMessage.setMessageTypeVersion(MessageType.TRAIN_COMPOSITION_MESSAGE.version());
        Company recipient = companyRepository.findByCode("0084").orElseThrow(EntityNotFoundException::new);
        trainCompositionMessage.setRecipient(recipient);
        Company sender = companyRepository.findByCode("9001").orElseThrow(EntityNotFoundException::new);
        trainCompositionMessage.setSender(sender);
        trainCompositionMessage.setSenderReference("RLS-9001-"+securityContext.getCompanyCode()+"-train:"+train.getId()+"-"+now.toString());
        trainCompositionMessage.setMessageIdentifier(tempRandomUUID);
        info.taf_jsg.schemes.TrainCompositionMessage trainCompositionMessage1 = TrainCompositionMessageXmlMapper.map(trainCompositionMessage);


        StringWriter xmlMessage = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
            jaxbMarshaller.marshal(trainCompositionMessage1, xmlMessage);

            InputStream xsdStream = new ClassPathResource("nl.rls.ci.controller/tafCatCompleteSector.xsd").getInputStream();
            InputStream xmlStream = new ByteArrayInputStream(xmlMessage.toString().getBytes());
            validateAgainstXSD(xmlStream, xsdStream);
            String xmlMessageString = xmlMessage.toString();

            UicSendMessageDto uicSendMessageDto = new UicSendMessageDto();
            UICMessageDto uicMessageDto = new UICMessageDto();
            uicMessageDto.setMessage(xmlMessageString);
            uicMessageDto.setEncoding("UTF-8");
            // TODO make this dynamic
            uicMessageDto.setSenderAlias("52.213.164.139");
            uicSendMessageDto.setUicMessageDto(uicMessageDto);
            uicSendMessageDto.setCompressed(false);
            uicSendMessageDto.setEncrypted(false);
            uicSendMessageDto.setSigned(false);
            uicSendMessageDto.setMessageIdentifier(tempRandomUUID);
            // TODO make this dynamic
            uicSendMessageDto.setMessageLiHost("52.213.164.139");
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<LiTechnicalAck> liTechnicalAckResponseEntity = restTemplate.postForEntity(System.getenv("CI_URL") + "/messages", uicSendMessageDto, LiTechnicalAck.class);
            CustomMessageStatus customMessageStatus = CustomMessageStatusMapper.map(Objects.requireNonNull(liTechnicalAckResponseEntity.getBody()));
            customMessageStatus.setTrain(train);
            train.getCustomMessageStatuses().add(customMessageStatus);
            return dozerBeanMapper.map(trainRepository.save(train), TrainDto.class);

        } catch (JAXBException | IOException | SAXException | HttpServerErrorException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static boolean validateAgainstXSD(InputStream xml, InputStream xsd) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(xsd));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xml));
        return true;
    }
}
