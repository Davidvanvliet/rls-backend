package nl.rls.composer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.message.MessageHeader;

public interface MessageHeaderRepository extends CrudRepository<MessageHeader, Integer> {
	List<MessageHeader> findByOwnerId(int ownerId);
}