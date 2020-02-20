package nl.rls.composer.repository;

import nl.rls.composer.domain.message.MessageReference;
import org.springframework.data.repository.CrudRepository;

public interface MessageReferenceRepository extends CrudRepository<MessageReference, Integer> {
}

