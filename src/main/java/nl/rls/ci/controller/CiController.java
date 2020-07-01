package nl.rls.ci.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
import org.xml.sax.SAXException;

import io.swagger.annotations.ApiOperation;
import nl.rls.auth.config.SecurityContext;
import nl.rls.ci.domain.CiMessage;
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
@RequestMapping(BaseURL.BASE_PATH + CiController.PATH)
public class CiController {
    public static final String PATH = "/messages";
    private static final Logger log = LoggerFactory.getLogger(CiController.class);
    private final CiRepository ciRepository;
    private final SecurityContext securityContext;
    private final CiService ciService;

    public CiController(CiRepository ciRepository, SecurityContext securityContext, CiService ciService) {
        this.ciRepository = ciRepository;
        this.securityContext = securityContext;
        this.ciService = ciService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets a all stored messages for a client, no filtering")
    public ResponseEntity<List<CiDto>> getAll() {
        int ownerId = securityContext.getOwnerId();
        List<CiMessage> ciMessages = ciRepository.findByOwnerId(ownerId);
        List<CiDto> messages = new ArrayList<CiDto>();
        for (CiMessage ciMessage : ciMessages) {
            messages.add(CiDtoMapper.map(ciMessage));
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Gets an idividual stored message.")
    public ResponseEntity<CiDto> getMessage(@PathVariable Integer id) {
        int ownerId = securityContext.getOwnerId();
        Optional<CiMessage> ciMessage = ciRepository.findByIdAndOwnerId(id, ownerId);
        if (ciMessage.isPresent()) {
            return ResponseEntity.ok(CiDtoMapper.map(ciMessage.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	/**
	 * Maakt een nieuw CI bericht aan.
	 * 
	 * @param messageXml
	 * @return de link naar het CI object/resource
	 */
//	@Transactional
//	@ApiOperation(value = "Stores a CI (XML-)message for a client (UicRequest). This message is not send.")
//	public ResponseEntity<?> postMessage(@RequestBody String messageXml) {
//		System.out.println("POST XML message to databases");
//		CiMessage ciMessage = ciService.makeCiMessage(messageXml);
//		return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
//				.build();
//	}

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
    public ResponseEntity<String> sendMessage(@PathVariable int id) {
        log.debug("sendMessage: " + id);
        Optional<CiMessage> optional = ciRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        String message = ciService.sendMessage(optional.get());
        if (message != null) {
            System.out.println(message);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.status(406).build();

        }

    }
    
    /**
     * Maakt een nieuw CI bericht aan.
     *
     * @param messageXml
     * @return de link naar het CI object/resource
     */
	@Transactional
	@PutMapping(value = "/xml/{messageIdentifier}")
	@ApiOperation(value = "Stores a CI (XML-)message for a client (UicRequest). This message is not send.")
	public ResponseEntity<?> postMessage(@PathVariable String messageIdentifier, @RequestBody String messageXml) {
		System.out.println("POST XML message to databases");
		CiMessage ciMessage = null;
		try {
			ciMessage = ciService.makeCiMessage(messageIdentifier, messageXml);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.created(linkTo(methodOn(CiController.class).getMessage(ciMessage.getId())).toUri())
				.build();
	}

}
