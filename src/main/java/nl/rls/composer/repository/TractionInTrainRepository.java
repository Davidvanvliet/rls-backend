package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.TractionInTrain;

public interface TractionInTrainRepository extends CrudRepository<TractionInTrain, Integer> {
}
