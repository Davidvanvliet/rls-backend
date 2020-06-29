package nl.rls.ci.controller;

import nl.rls.auth.config.SecurityContext;
import nl.rls.ci.domain.XmlMessage;
import nl.rls.ci.url.BaseURL;
import nl.rls.composer.repository.XmlMessageRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping(BaseURL.BASE_PATH + XmlMessageController.PATH)
public class XmlMessageController {
    public static final String PATH = "/xmlmessages";
    private final SecurityContext securityContext;
    private final XmlMessageRepository xmlMessageRepository;

    public XmlMessageController(SecurityContext securityContext, XmlMessageRepository xmlMessageRepository) {
        this.securityContext = securityContext;
        this.xmlMessageRepository = xmlMessageRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @Transactional
    public ResponseEntity<String> getById(@PathVariable Integer id) {
        int ownerId = securityContext.getOwnerId();
        Optional<XmlMessage> xmlMessage = xmlMessageRepository.findByIdAndOwnerId(id, ownerId);
        if (xmlMessage.isPresent()) {
            return ResponseEntity.ok(xmlMessage.get().getMessage());
        } else {
            throw new EntityNotFoundException();
        }
    }
}
