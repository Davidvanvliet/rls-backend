package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.code.TrainActivityType;

public interface TrainActivityTypeRepository extends CrudRepository<TrainActivityType, Integer> {
	Optional<TrainActivityType> findByCode(String code);
}
