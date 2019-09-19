package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.TrainRunningTechData;

public interface TrainRunningTechDataRepository extends CrudRepository<TrainRunningTechData, Integer> {
}