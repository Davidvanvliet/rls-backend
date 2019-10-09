package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.ci.domain.XmlMessage;

public interface XmlMessageRepository extends CrudRepository<XmlMessage, Integer> {
	Optional<XmlMessage> findByIdAndOwnerId(Integer id, int ownerId);
}
