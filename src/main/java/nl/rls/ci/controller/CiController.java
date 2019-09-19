package nl.rls.ci.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.rls.ci.aa.security.SecurityContext;
import nl.rls.ci.domain.CiMessage;
import nl.rls.ci.domain.UicHeader;
import nl.rls.ci.domain.UicRequest;
import nl.rls.ci.repository.CiRepository;
import nl.rls.ci.rest.dto.CiDto;
import nl.rls.ci.rest.dto.CiPostDto;
import nl.rls.ci.rest.dto.mapper.CiDtoMapper;
import nl.rls.ci.service.CiHostName;
import nl.rls.ci.service.CiService;

/**
 * @author berend.wilkens
 * Genereert XML berichten voor de common interface
 */
@RestController
@RequestMapping(value = "/api/v1/ci/messages")
public class CiController {
	@Autowired
	private CiRepository ciRepository;
	@Autowired
	private SecurityContext securityContext;
	@Autowired
	private CiService ciService;

	private static final Logger log = LoggerFactory.getLogger(CiController.class);

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ResponseEntity<String> getHello() {
		return ResponseEntity.ok("Hello world");
	}

	@RequestMapping(method = RequestMethod.GET)
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> postMessage(@RequestBody String messageXml) {
		int ownerId = securityContext.getOwnerId();
		// maak de wrapper voor het bericht
		CiMessage ciMessage = new CiMessage();
		ciMessage.setOwnerId(ownerId);
		/*
		 * maak de het bericht voor de common interface = SOAP body
		 */
		UicRequest uicRequest = new UicRequest();
		// zet het specifieke bericht, bijv TCM, dit komt van de client
		System.out.println("postMessage XML message: "+ messageXml);
		uicRequest.setMessage(messageXml);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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
	@RequestMapping(value = "/{id}/", method = RequestMethod.POST)
	public ResponseEntity<?> sendMessage(@PathVariable int id, @RequestParam(required = true) String action) {
		log.debug("sendMessage: "+id+" "+action);
		Optional<CiMessage> optional = ciRepository.findById(id);
		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		System.out.println("sendMessage XML message: "+ optional.get().getUicRequest().getMessage());
		if (ciService.sendMessage(optional.get())) {
			optional.get().setPosted(true);
			optional.get().setPostDate(new Date());
		} else {
			optional.get().setPosted(false);
			optional.get().setPostDate(null);
		}
		ciRepository.save(optional.get());

		return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(optional.get().getId())).toUri())
				.build();
	}

}
