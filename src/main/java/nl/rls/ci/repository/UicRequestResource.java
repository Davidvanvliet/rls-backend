package nl.rls.ci.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.ci.domain.UicRequest;

public interface UicRequestResource extends CrudRepository<UicRequest, Integer> {
	Optional<UicRequest> findByIdAndOwnerId(int id, int ownerId);
	List<UicRequest> findByOwnerId(int ownerId);
}