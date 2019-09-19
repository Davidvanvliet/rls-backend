package nl.rls.ci.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.ci.domain.CiMessage;

public interface CiRepository extends CrudRepository<CiMessage, Integer> {
	Optional<CiMessage> findByIdAndOwnerId(int id, int ownerId);

	List<CiMessage> findByOwnerId(int ownerId);
}
