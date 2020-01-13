package nl.rls.composer.repository;

import org.springframework.data.repository.CrudRepository;

import nl.rls.composer.domain.WagonType;

public interface WagonTypeRepository extends CrudRepository<WagonType, Integer> {
}
