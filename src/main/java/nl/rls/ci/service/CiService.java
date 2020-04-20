package nl.rls.ci.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.domain.UicHeader;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.domain.UicResponse;
import nl.rls.ci.repository.CiRepository;
import nl.rls.ci.soap.dto.LITechnicalAck;
import nl.rls.ci.soap.dto.mapper.CiDtoMapper;
import nl.rls.ci.soapinterface.LIReceiveMessageService;
import nl.rls.ci.soapinterface.UICMessage;
import nl.rls.ci.soapinterface.UICMessageResponse;
import nl.rls.ci.soapinterface.UICReceiveMessage;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

/**
 * @author berend.wilkens Localhost: 145.89.169.134
 */
@Component
public class CiService {
    private static final Logger log = LoggerFactory.getLogger(CiService.class);
    private final CiRepository ciRepository;
    private final SecurityContext securityContext;

    public CiService(CiRepository ciRepository, SecurityContext securityContext) {
        this.ciRepository = ciRepository;
        this.securityContext = securityContext;
    }

    @Transactional
    public String sendMessage(CiMessage ciMessage) {
        String message = "";
        log.info("sendMessage 1 (ciMessage): " + ciMessage);
        UICMessage uicMessage = CiDtoMapper.map(ciMessage.getUicRequest());
        log.info("sendMessage 2 (uicMessage): " + uicMessage.getMessage());
        System.out.println("WSDL_LOCATION: " + LIReceiveMessageService.LIRECEIVEMESSAGESERVICE_WSDL_LOCATION);

        LIReceiveMessageService service = new LIReceiveMessageService();
        log.info("sendMessage 2a (service.getServiceName()): " + service.getServiceName());
        UICReceiveMessage uicReceiveMessage = service.getUICReceiveMessagePort();
        // new SSLTool();
        // SSLTool.disableCertificateValidation();
        UICMessageResponse uicMessageResponse = null;
        log.info("sendMessage 2b (uicReceiveMessage): " + uicReceiveMessage.toString());
        try {
            uicMessageResponse = uicReceiveMessage.uicMessage(uicMessage,
                    ciMessage.getUicHeader().getMessageIdentifier(), ciMessage.getUicHeader().getMessageLiHost(), false,
                    false, false);
            log.info("sendMessage 3b (uicMessageResponse): " + uicMessageResponse.getReturn().toString());
            LITechnicalAck technicalAck = unMarshallResponse(uicMessageResponse.getReturn().toString());
            System.out.println(technicalAck);
            UicResponse uicResponse = CiDtoMapper.map(technicalAck);
            ciMessage.setUicResponse(uicResponse);
            log.info("sendMessage 4 (uicResponse): " + uicResponse);
            message = uicResponse.getResponseStatus();
        } catch (Exception e) {
            ciMessage.setHttpError(e.getMessage());
            message = e.getMessage();
            log.info("Error " + e.getMessage());
        }
        ciMessage.setPosted(true);
        ciMessage.setPostDate(new Date());
        ciRepository.save(ciMessage);
        log.info("sendMessage 5 (uicResponse): " + ciMessage);
        return message;
    }

    public String makeTcmMessage(TrainCompositionMessage trainCompositionMessage) {
        info.taf_jsg.schemes.TrainCompositionMessage trainCompositionXmlMessage = TrainCompositionMessageXmlMapper
                .map(trainCompositionMessage);
        StringWriter xmlMessage = new StringWriter();
        try {
            File file = new File("train_composition_message.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            jaxbMarshaller.marshal(trainCompositionXmlMessage, file);
            jaxbMarshaller.marshal(trainCompositionXmlMessage, System.out);

            jaxbMarshaller.marshal(trainCompositionXmlMessage, xmlMessage);
            File xsdFile = new File("taf_cat_complete_sector.xsd");
            InputStream xsdStream = new FileInputStream(xsdFile);

            System.out.println("validating XML message: " + xmlMessage);
            System.out.println("xsdStream: " + xsdStream.toString());

            InputStream xmlStream = new ByteArrayInputStream(xmlMessage.toString().getBytes());
            validateAgainstXSD(xmlStream, xsdStream);

        } catch (JAXBException | SAXException | IOException e) {
            e.printStackTrace();
            //
        }
        return xmlMessage.toString();
    }

    public CiMessage makeCiMessage(String messageIdentifier, String messageXml)
            throws SAXException, IOException {
        InputStream targetStream = new ByteArrayInputStream(messageXml.getBytes());
        File file = new File("taf_cat_complete_sector.xsd");
        InputStream xsdStream = new FileInputStream(file);
        System.out.println(messageXml);
        validateAgainstXSD(targetStream, xsdStream);
        int ownerId = securityContext.getOwnerId();
        // maak de wrapper voor het bericht
        CiMessage ciMessage = new CiMessage();
        ciMessage.setOwnerId(ownerId);
        /*
         * maak de het bericht voor de common interface = SOAP body
         */
        UicRequest uicRequest = new UicRequest();
        uicRequest.setOwnerId(ownerId);
        // zet het specifieke bericht, bijv TCM, dit komt van de client
        System.out.println("postMessage XML message: " + messageXml);
        uicRequest.setMessage(messageXml);
        uicRequest.setSenderAlias("82.217.100.12");
        ciMessage.setUicRequest(uicRequest);

        /*
         * maak de het bericht voor de common interface = SOAP header
         */
        UicHeader uicHeader = new UicHeader();
        uicHeader.setMessageIdentifier(messageIdentifier);
        uicHeader.setMessageLiHost("82.217.100.12");
        ciMessage.setUicHeader(uicHeader);
        ciMessage.setCreateDate(new Date());
        ciMessage = ciRepository.save(ciMessage);
        return ciMessage;
    }

    private LITechnicalAck unMarshallResponse(String xmlString) {
        LITechnicalAck technicalAck = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LITechnicalAck.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            technicalAck = (LITechnicalAck) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return technicalAck;
    }

    public boolean validate() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setValidating(true);

        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
                "http://domain.com/mynamespace/mySchema.xsd");
        try {
            DocumentBuilder parser = factory.newDocumentBuilder();
            @SuppressWarnings("unused")
            Document doc = parser.parse("data.xml");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean validateAgainstXSD(InputStream xml, InputStream xsd) throws SAXException, IOException {
        System.out.println("Starting validateAgainstXSD");
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        System.out.println("factory.newSchema(new StreamSource(xsd))");
        Schema schema = factory.newSchema(new StreamSource(xsd));
        System.out.println("schema.newValidator()");
        Validator validator = schema.newValidator();
        System.out.println("Just before validating");
        validator.validate(new StreamSource(xml));
        return true;
    }
}
