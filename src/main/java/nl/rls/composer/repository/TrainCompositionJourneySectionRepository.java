package nl.rls.composer.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.Train;
import nl.rls.composer.domain.TrainCompositionJourneySection;

public interface TrainCompositionJourneySectionRepository extends CrudRepository<TrainCompositionJourneySection, Integer> {

	Optional<TrainCompositionJourneySection> findByIdAndOwnerId(int id, int ownerId);

	Iterable<TrainCompositionJourneySection> findByTrainAndOwnerId(Train train, int ownerId);
}