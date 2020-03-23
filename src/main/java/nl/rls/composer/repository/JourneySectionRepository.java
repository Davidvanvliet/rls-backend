package nl.rls.composer.repository;

import nl.rls.composer.domain.JourneySection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JourneySectionRepository extends CrudRepository<JourneySection, Integer> {
    Optional<JourneySection> findByIdAndOwnerId(int id, int ownerId);

    List<JourneySection> findByTrainIdAndOwnerId(int trainId, int ownerId);
}