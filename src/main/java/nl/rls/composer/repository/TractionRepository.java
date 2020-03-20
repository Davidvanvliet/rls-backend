package nl.rls.composer.repository;

import nl.rls.composer.domain.Traction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TractionRepository extends CrudRepository<Traction, Integer> {
    Optional<Traction> findByIdAndOwnerId(int id, int ownerId);

    List<Traction> findByOwnerId(int ownerId);
}
