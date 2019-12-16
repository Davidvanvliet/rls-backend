package nl.rls.ci.service;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.domain.UicHeader;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.domain.UicResponse;
import nl.rls.ci.domain.XmlMessage;
import nl.rls.ci.repository.CiRepository;
import nl.rls.ci.soap.dto.LIReceiveMessageService;
import nl.rls.ci.soap.dto.LITechnicalAck;
import nl.rls.ci.soap.dto.UICMessage;
import nl.rls.ci.soap.dto.UICMessageResponse;
import nl.rls.ci.soap.dto.UICReceiveMessage;
import nl.rls.ci.soap.dto.mapper.CiDtoMapper;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.repository.XmlMessageRepository;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

@Component
public class CiService {
	private static final Logger log = LoggerFactory.getLogger(CiService.class);
	@Autowired
	private CiRepository ciRepository;
	@Autowired
	private XmlMessageRepository xmlMessageRepository;
	@Autowired
	private SecurityContext securityContext;

	@Transactional
	public boolean sendMessage(CiMessage ciMessage) {
		log.info("sendMessage 1 (ciMessage): " + ciMessage);
		UICMessage uicMessage = CiDtoMapper.map(ciMessage.getUicRequest());
		log.info("sendMessage 2 (uicMessage): " + uicMessage);
		System.out.println("WSDL_LOCATION: " + LIReceiveMessageService.LIRECEIVEMESSAGESERVICE_WSDL_LOCATION);

		LIReceiveMessageService liReceiveMessageService = new LIReceiveMessageService();
		UICReceiveMessage uicReceiveMessage = liReceiveMessageService.getUICReceiveMessagePort();
		UICMessageResponse uicMessageResponse = uicReceiveMessage.uicMessage(uicMessage,
				ciMessage.getUicHeader().getMessageIdentifier(), ciMessage.getUicHeader().getMessageLiHost(),
				ciMessage.getUicHeader().isCompressed(), ciMessage.getUicHeader().isEncrypted(),
				ciMessage.getUicHeader().isSigned());
		log.info("sendMessage 3 (uicMessageResponse): " + uicMessageResponse.getReturn());
		LITechnicalAck technicalAck = unMarshallResponse(uicMessageResponse.getReturn().toString());
		UicResponse uicResponse = CiDtoMapper.map(technicalAck);
		ciMessage.setUicResponse(uicResponse);
		log.info("sendMessage 4 (uicResponse): " + uicResponse);
		ciMessage.setPosted(true);
		ciMessage.setPostDate(new Date());
		ciRepository.save(ciMessage);
		return true;
	}

	public String makeXmlMessage(TrainCompositionMessage trainCompositionMessage) {
		info.taf_jsg.schemes.TrainCompositionMessage trainCompositionXmlMessage = TrainCompositionMessageXmlMapper
				.map(trainCompositionMessage);
		StringWriter xmlMessage = new StringWriter();
		try {
			File file = new File("train_composition_message.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.marshal(trainCompositionXmlMessage, file);
			jaxbMarshaller.marshal(trainCompositionXmlMessage, System.out);

			jaxbMarshaller.marshal(trainCompositionXmlMessage, xmlMessage);

		} catch (JAXBException e) {
			e.printStackTrace();
			//
		}
		return xmlMessage.toString();
	}

	public CiMessage makeCiMessage(String messageXml) {
		CiMessage ciMessage = new CiMessage();
		int ownerId = securityContext.getOwnerId();
		// maak de wrapper voor het bericht
		ciMessage.setOwnerId(ownerId);
		/*
		 * maak de het bericht voor de common interface = SOAP body
		 */
		UicRequest uicRequest = new UicRequest();
		uicRequest.setOwnerId(ownerId);
		// zet het specifieke bericht, bijv TCM, dit komt van de client
		System.out.println("postMessage XML message: " + messageXml);
		XmlMessage xmlMessage = new XmlMessage();
		xmlMessage.setOwnerId(ownerId);
		xmlMessage.setMessage(messageXml.toString());
		xmlMessageRepository.save(xmlMessage);
		uicRequest.setMessage(xmlMessage);
		uicRequest.setSignature("signature");
		ciMessage.setUicRequest(uicRequest);

		/*
		 * maak de het bericht voor de common interface = SOAP header
		 */
		UicHeader uicHeader = new UicHeader();
		uicHeader.setMessageIdentifier(UUID.randomUUID().toString());
		uicHeader.setMessageLiHost(CiHostName.getPublicHostName());
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
			System.out.println(technicalAck);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return technicalAck;
	}

}
