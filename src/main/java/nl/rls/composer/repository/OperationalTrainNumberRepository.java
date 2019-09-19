package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.OperationalTrainNumber;

public interface OperationalTrainNumberRepository extends CrudRepository<OperationalTrainNumber, Integer> {
	Optional<OperationalTrainNumber> findByValue(String value);
}
