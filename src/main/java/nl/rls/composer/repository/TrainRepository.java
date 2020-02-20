package nl.rls.composer.repository;

import nl.rls.composer.domain.Train;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrainRepository extends CrudRepository<Train, Integer> {
    Optional<Train> findByIdAndOwnerId(int id, int ownerId);

    Iterable<Train> findByOwnerId(int ownerId);
}
