package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonInTrain;

public interface WagonInTrainRepository extends CrudRepository<WagonInTrain, Integer> {

	Optional<WagonInTrain> findByIdAndOwnerId(Integer id, Integer ownerId);
}
