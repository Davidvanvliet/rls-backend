package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonTechData;

public interface WagonTechDataRepository extends CrudRepository<WagonTechData, Integer> {
}
