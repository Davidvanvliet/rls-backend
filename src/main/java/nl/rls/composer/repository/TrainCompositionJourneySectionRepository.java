package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.TrainCompositionJourneySection;

public interface TrainCompositionJourneySectionRepository extends CrudRepository<TrainCompositionJourneySection, Integer> {

	Iterable<TrainCompositionJourneySection> findByOwnerId(int ownerId);

	Optional<TrainCompositionJourneySection> findByIdAndOwnerId(int id, int ownerId);
}