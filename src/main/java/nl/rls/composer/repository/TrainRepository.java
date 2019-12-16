package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Train;

public interface TrainRepository extends CrudRepository<Train, Integer> {
	Optional<Train> findByIdAndOwnerId(int id, int ownerId);
	Iterable<Train> findByOwnerId(int ownerId);
}
