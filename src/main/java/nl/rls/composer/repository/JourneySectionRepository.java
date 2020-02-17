package nl.rls.composer.repository;

import nl.rls.composer.domain.JourneySection;
import nl.rls.composer.domain.Train;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JourneySectionRepository extends CrudRepository<JourneySection, Integer> {
    Optional<JourneySection> findByIdAndOwnerId(int id, int ownerId);

    Iterable<JourneySection> findByTrainAndOwnerId(Train train, int ownerId);
}