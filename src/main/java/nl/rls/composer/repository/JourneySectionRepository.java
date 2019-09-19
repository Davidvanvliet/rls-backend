package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.JourneySection;

public interface JourneySectionRepository extends CrudRepository<JourneySection, Integer> {
}
