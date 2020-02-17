package nl.rls.ci.repository;

import nl.rls.ci.domain.UicRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UicRequestResource extends CrudRepository<UicRequest, Integer> {
    Optional<UicRequest> findByIdAndOwnerId(int id, int ownerId);

    List<UicRequest> findByOwnerId(int ownerId);
}