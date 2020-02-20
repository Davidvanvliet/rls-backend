package nl.rls.composer.repository;

import nl.rls.ci.domain.XmlMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface XmlMessageRepository extends CrudRepository<XmlMessage, Integer> {
    Optional<XmlMessage> findByIdAndOwnerId(Integer id, int ownerId);
}
