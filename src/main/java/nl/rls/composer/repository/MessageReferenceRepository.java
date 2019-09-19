package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.message.MessageReference;

public interface MessageReferenceRepository extends CrudRepository<MessageReference, Integer> {
}

