package nl.rls.composer.repository;

import nl.rls.composer.domain.message.MessageHeader;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageHeaderRepository extends CrudRepository<MessageHeader, Integer> {
    List<MessageHeader> findByOwnerId(int ownerId);
}