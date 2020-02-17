package nl.rls.composer.repository;

import nl.rls.composer.domain.code.TrainActivityType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrainActivityTypeRepository extends CrudRepository<TrainActivityType, Integer> {
    Optional<TrainActivityType> findByCode(String code);
}
