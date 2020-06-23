package nl.rls.composer.repository;

import nl.rls.composer.domain.WagonInTrain;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WagonInTrainRepository extends CrudRepository<WagonInTrain, Integer> {

    Optional<WagonInTrain> findById(Integer id);
}
