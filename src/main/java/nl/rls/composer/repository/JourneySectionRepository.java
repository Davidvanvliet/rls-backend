package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.JourneySection;

public interface JourneySectionRepository extends CrudRepository<JourneySection, Integer> {
	Optional<JourneySection> findByIdAndOwnerId(int id, int ownerId);

	Iterable<JourneySection> findByTrainAndOwnerId(Train train, int ownerId);
}