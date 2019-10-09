package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.TrainCompositionMessage;

public interface TrainCompositionMessageRepository  extends CrudRepository<TrainCompositionMessage, Integer> {
	Optional<TrainCompositionMessage> findByIdAndOwnerId(int id, int ownerId);
	Iterable<TrainCompositionMessage> findByOwnerId(int ownerId);

}
