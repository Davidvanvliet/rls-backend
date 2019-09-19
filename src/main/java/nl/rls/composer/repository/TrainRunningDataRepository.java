package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.TrainRunningData;

public interface TrainRunningDataRepository extends CrudRepository<TrainRunningData, Integer> {

	Optional<TrainRunningData> findByIdAndOwnerId(int id, int ownerId);
}
