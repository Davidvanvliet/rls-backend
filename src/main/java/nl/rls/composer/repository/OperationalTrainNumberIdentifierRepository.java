package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.OperationalTrainNumberIdentifier;

public interface OperationalTrainNumberIdentifierRepository
		extends CrudRepository<OperationalTrainNumberIdentifier, Integer> {
}
