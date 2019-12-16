package nl.rls.ci.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.domain.UicResponse;
import nl.rls.ci.repository.CiRepository;
import nl.rls.ci.rest.dto.CiDto;
import nl.rls.ci.rest.dto.CiPostDto;
import nl.rls.ci.rest.dto.mapper.CiDtoMapper;
import nl.rls.ci.service.CiService;
import nl.rls.ci.url.BaseURL;

/**
 * @author berend.wilkens
 * Genereert XML berichten voor de common interface
 */
@RestController
@RequestMapping(BaseURL.BASE_PATH+CiController.PATH)
public class CiController {
	public static final String PATH = "messages";
	@Autowired
	private CiRepository ciRepository;
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private CiService ciService;

	private static final Logger log = LoggerFactory.getLogger(CiController.class);

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets a all stored messages for a client, no filtering")
	public ResponseEntity<Resources<CiDto>> getAll() {
		int ownerId = securityContext.getOwnerId();
		List<CiMessage> ciMessages = ciRepository.findByOwnerId(ownerId);
		List<CiDto> messages = new ArrayList<CiDto>();
		for (CiMessage ciMessage : ciMessages) {
			messages.add(CiDtoMapper.map(ciMessage));
		}
		Link cisLink = linkTo(methodOn(CiController.class).getAll()).withSelfRel();
		Resources<CiDto> resourceList = new Resources<CiDto>(messages, cisLink);
		return ResponseEntity.ok(resourceList);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Gets an idividual stored message.")
	public ResponseEntity<CiDto> getMessage(@PathVariable Integer id) {
		int ownerId = securityContext.getOwnerId();
		Optional<CiMessage> ciMessage = ciRepository.findByIdAndOwnerId(id, ownerId);
		if (ciMessage.isPresent()) {
			return ResponseEntity.ok(CiDtoMapper.map(ciMessage.get()));
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
	@Transactional
	@PostMapping(value = "/")
	@ApiOperation(value = "Stores a CI (XML-)message for a client (UicRequest). This message is not send.")
	public ResponseEntity<?> postMessage(@RequestBody String messageXml) {
		CiMessage ciMessage = ciService.makeCiMessage(messageXml);
		return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
				.build();
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "Updates a CI (XML-)message for a client (UicRequest). This message is not send.")
	public ResponseEntity<Object> putMessage(@PathVariable int id, @RequestBody CiPostDto ciPostDto)
			throws URISyntaxException {
		CiMessage ciMessage = CiDtoMapper.map(ciPostDto);
		ciMessage.setPosted(false);
		ciMessage = ciRepository.save(ciMessage);
		return ResponseEntity.ok(null);
	}

	/**
	 * @param id
	 * @param action
	 * @return
	 */
	@Transactional
	@PostMapping(value = "/{id}")
	@ApiOperation(value = "Sends a previously stored CI (XML-)message to the Common Interface (UicRequest).")
	public ResponseEntity<UicResponse> sendMessage(@PathVariable int id) {
		log.debug("sendMessage: "+id);
		Optional<CiMessage> optional = ciRepository.findById(id);
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		System.out.println("sendMessage XML message: "+ optional.get().getUicRequest().getMessage());
		if (ciService.sendMessage(optional.get())) {
			return ResponseEntity.accepted().build();
		} else {
			return ResponseEntity.status(406).build();
			
		}

	}

}
