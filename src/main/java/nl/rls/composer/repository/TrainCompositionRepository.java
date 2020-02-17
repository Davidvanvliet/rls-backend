package nl.rls.composer.repository;

import nl.rls.composer.domain.TrainComposition;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrainCompositionRepository extends CrudRepository<TrainComposition, Integer> {

    Optional<TrainComposition> findByIdAndOwnerId(Integer id, int ownerId);

}
