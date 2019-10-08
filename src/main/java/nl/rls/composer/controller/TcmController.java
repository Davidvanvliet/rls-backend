package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.controller.CiController;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.domain.UicHeader;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.repository.CiRepository;
import nl.rls.ci.service.CiHostName;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.TrainCompositionMessage;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import nl.rls.composer.xml.mapper.TrainCompositionMessageXmlMapper;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"ci/tcm")
public class TcmController {
	@Autowired
	private TrainCompositionMessageRepository trainCompositionMessageRepository;
	@Autowired
	private CiRepository ciRepository;
	@Autowired
	private SecurityContext securityContext;


	@GetMapping
	public ResponseEntity<String> getTrains() {
		return new ResponseEntity<>("Hello world", HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getTcmIdent(@PathVariable Integer id) {
		StringWriter messageXml = new StringWriter();
		CiMessage ciMessage = new CiMessage();
		Optional<TrainCompositionMessage> trainCompositionMessage = trainCompositionMessageRepository.findById(id);
		if (trainCompositionMessage.isPresent()) {
			System.out.println(trainCompositionMessage.get());
			info.taf_jsg.schemes.TrainCompositionMessage trainCompositionXmlMessage = TrainCompositionMessageXmlMapper.map(trainCompositionMessage.get());
			try {

				File file = new File("train_composition_message.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(info.taf_jsg.schemes.TrainCompositionMessage.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// output pretty printed
//				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
				jaxbMarshaller.marshal(trainCompositionXmlMessage, file);
				jaxbMarshaller.marshal(trainCompositionXmlMessage, System.out);
				
				jaxbMarshaller.marshal(trainCompositionXmlMessage, messageXml);

			} catch (JAXBException e) {
				e.printStackTrace();
				//
			}

			int ownerId = securityContext.getOwnerId();
			// maak de wrapper voor het bericht
			ciMessage.setOwnerId(ownerId);
			/*
			 * maak de het bericht voor de common interface = SOAP body
			 */
			UicRequest uicRequest = new UicRequest();
			// zet het specifieke bericht, bijv TCM, dit komt van de client
			System.out.println("postMessage XML message: "+ messageXml);
			uicRequest.setMessage(messageXml.toString());
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
			return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
					.build();

			
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(ciMessage.toString(), headers, HttpStatus.CREATED);
	}
}
