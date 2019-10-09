package nl.rls.composer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.controller.CiController;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.service.CiService;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.domain.TrainCompositionMessage;
import nl.rls.composer.repository.TrainCompositionMessageRepository;

@RestController
@RequestMapping(BaseURL.BASE_PATH+"ci/tcm")
public class TcmController {
	@Autowired
	private TrainCompositionMessageRepository trainCompositionMessageRepository;
	@Autowired
	private CiService ciService;
	@Autowired
	private SecurityContext securityContext;

	@ApiOperation(value = "Constructs a tcm-message from data and puts it into de CI-buffer")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> createTcm(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<TrainCompositionMessage> trainCompositionMessage = trainCompositionMessageRepository.findByIdAndOwnerId(id, ownerId);
		if (trainCompositionMessage.isPresent()) {
			System.out.println(trainCompositionMessage.get());
			String xmlMessage = ciService.makeXmlMessage(trainCompositionMessage.get());
			CiMessage ciMessage = ciService.makeCiMessage(xmlMessage);
			return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
					.body(ciMessage.toString());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
