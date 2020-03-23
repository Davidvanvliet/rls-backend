package nl.rls.composer.repository;

import nl.rls.composer.domain.message.TrainCompositionMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TrainCompositionMessageRepository extends CrudRepository<TrainCompositionMessage, Integer> {
    Optional<TrainCompositionMessage> findByIdAndOwnerId(int id, int ownerId);

    List<TrainCompositionMessage> findByOwnerId(int ownerId);

    boolean existsByIdAndOwnerId(int id, int ownerId);

}
