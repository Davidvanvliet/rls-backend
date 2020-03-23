package nl.rls.ci.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.service.CiService;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.message.TrainCompositionMessage;
import nl.rls.composer.repository.TrainCompositionMessageRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(BaseURL.BASE_PATH + "/tcm")
public class TcmController {
    private final TrainCompositionMessageRepository trainCompositionMessageRepository;
    private final CiService ciService;
    private final SecurityContext securityContext;

    public TcmController(TrainCompositionMessageRepository trainCompositionMessageRepository, CiService ciService, SecurityContext securityContext) {
        this.trainCompositionMessageRepository = trainCompositionMessageRepository;
        this.ciService = ciService;
        this.securityContext = securityContext;
    }

    @ApiOperation(value = "Constructs a tcm-message from data and puts it into de CI-buffer")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createTcm(@PathVariable Integer id) throws IOException, SAXException {
        int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionMessage> optional = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
		if (optional.isPresent()) {
			TrainCompositionMessage trainCompositionMessage = optional.get();
			System.out.println(trainCompositionMessage);
			String tcmMessage = ciService.makeTcmMessage(trainCompositionMessage);
			CiMessage ciMessage = ciService.makeCiMessage(trainCompositionMessage, tcmMessage);

			System.out.println("POST XML message to databases");
			return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
					.build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Maakt een nieuw CI bericht aan.
	 * 
	 * @param messageXml
	 * @return de link naar het CI object/resource
	 */
//	@Transactional
//	@PostMapping(value = "/")
//	@ApiOperation(value = "Stores a CI (XML-)message for a client (UicRequest). This message is not send.")
//	public ResponseEntity<?> postMessage(@RequestBody String messageXml) {
//		System.out.println("POST XML message to databases");
//		CiMessage ciMessage = ciService.makeCiMessage(messageXml);
//		return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
//				.build();
//	}
//

}
