package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonInTrain;

public interface WagonInTrainRepository extends CrudRepository<WagonInTrain, Integer> {
}
